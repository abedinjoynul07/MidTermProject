package com.shokal.custopapiwithrecyclerview.viewmodels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shokal.custopapiwithrecyclerview.BuildConfig
import com.shokal.custopapiwithrecyclerview.models.Article
import com.shokal.custopapiwithrecyclerview.services.NewsApi
import kotlinx.coroutines.launch

enum class NewsApiStatus { LOADING, ERROR, DONE }
class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val result = mutableListOf<Article>()
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
    }
}