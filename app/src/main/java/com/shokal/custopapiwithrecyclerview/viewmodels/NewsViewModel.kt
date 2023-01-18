package com.shokal.custopapiwithrecyclerview.viewmodels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.shokal.custopapiwithrecyclerview.BuildConfig
import com.shokal.custopapiwithrecyclerview.models.Article
import com.shokal.custopapiwithrecyclerview.models.LocalArticle
import com.shokal.custopapiwithrecyclerview.services.NewsApi
import kotlinx.coroutines.launch

enum class NewsApiStatus { LOADING, ERROR, DONE }
class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val localViewModel: LocalNewsViewModel
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status
    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> = _news

    private val _businessNews = MutableLiveData<List<Article>>()
    val businessNews: LiveData<List<Article>> = _businessNews

    private val _sportsNews = MutableLiveData<List<Article>>()
    val sportsNews: LiveData<List<Article>> = _sportsNews

    private val _technologyNews = MutableLiveData<List<Article>>()
    val technologyNews: LiveData<List<Article>> = _technologyNews

    val context: Context

    init {
        context = application.applicationContext
        localViewModel = LocalNewsViewModel(application)
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _news.value = NewsApi.retrofitService.getAllNews(BuildConfig.API_KEY, "*").articles

//                for (item in 0 until news.value!!.size) {
//                    Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
//                    val article = LocalArticle(
//                        0,
//                        news.value?.get(item)?.author.toString(),
//                        "all",
//                        0,
//                        news.value?.get(item)?.content.toString(),
//                        news.value?.get(item)?.description.toString(),
//                        news.value?.get(item)?.publishedAt.toString(),
//                        news.value?.get(item)?.title.toString(),
//                        news.value?.get(item)?.url.toString(),
//                        news.value?.get(item)?.urlToImage.toString()
//                    )
//                    localViewModel.addArticle(article)
//                }

                _businessNews.value = NewsApi.retrofitService.getBusinessNews(
                    BuildConfig.API_KEY, "business"
                ).articles
                _sportsNews.value =
                    NewsApi.retrofitService.getBusinessNews(BuildConfig.API_KEY, "sports").articles
                _technologyNews.value = NewsApi.retrofitService.getBusinessNews(
                    BuildConfig.API_KEY, "technology"
                ).articles
                _status.value = NewsApiStatus.DONE

            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                _status.value = NewsApiStatus.ERROR
                _news.value = listOf()
                _businessNews.value = listOf()
                _sportsNews.value = listOf()
                _technologyNews.value = listOf()
            }
        }

        //importDataToLocalDatabase()
    }

//    private fun importDataToLocalDatabase() {
//        viewModelScope.launch {
//            for (item in 0 until news.value!!.size) {
//                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
//                val article = LocalArticle(
//                    0,
//                    news.value?.get(item)?.author.toString(),
//                    "all",
//                    0,
//                    news.value?.get(item)?.content.toString(),
//                    news.value?.get(item)?.description.toString(),
//                    news.value?.get(item)?.publishedAt.toString(),
//                    news.value?.get(item)?.title.toString(),
//                    news.value?.get(item)?.url.toString(),
//                    news.value?.get(item)?.urlToImage.toString()
//                )
//                localViewModel.addArticle(article)
//            }
//        }
//    }

//    private suspend fun getBitmap(imageLink: String): Bitmap {
//        val loading = ImageLoader(context)
//        val request = ImageRequest.Builder(context).data(imageLink).build()
//        val result = (loading.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }
}