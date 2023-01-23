package com.shokal.custopapiwithrecyclerview.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shokal.custopapiwithrecyclerview.BuildConfig
import com.shokal.custopapiwithrecyclerview.models.Article
import com.shokal.custopapiwithrecyclerview.services.NewsApi
import kotlinx.coroutines.*

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
    private val _scienceNews = MutableLiveData<List<Article>>()
    val scienceNews: LiveData<List<Article>> = _scienceNews
    private val _healthNews = MutableLiveData<List<Article>>()
    val healthNews: LiveData<List<Article>> = _healthNews

    @SuppressLint("StaticFieldLeak")
    val context: Context

    init {
        context = application.applicationContext
        localViewModel = LocalNewsViewModel(application)
        getNews()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            _status.postValue(NewsApiStatus.LOADING)
            try {
                _news.postValue(
                    NewsApi.retrofitService.getAllNews(
                        BuildConfig.API_KEY,
                        "general"
                    ).articles
                )
                _businessNews.postValue(
                    NewsApi.retrofitService.getBusinessNews(
                        BuildConfig.API_KEY, "business"
                    ).articles
                )
                _sportsNews.postValue(
                    NewsApi.retrofitService.getBusinessNews(
                        BuildConfig.API_KEY,
                        "sports"
                    ).articles
                )
                _technologyNews.postValue(
                    NewsApi.retrofitService.getBusinessNews(
                        BuildConfig.API_KEY, "technology"
                    ).articles
                )
                _scienceNews.postValue(
                    NewsApi.retrofitService.getBusinessNews(
                        BuildConfig.API_KEY, "science"
                    ).articles
                )
                _healthNews.postValue(
                    NewsApi.retrofitService.getBusinessNews(
                        BuildConfig.API_KEY, "health"
                    ).articles
                )
                _status.postValue(NewsApiStatus.DONE)

            } catch (e: Exception) {
                withContext(Dispatchers.IO) {
                    _status.postValue(NewsApiStatus.ERROR)
                    _news.postValue(listOf())
                    _businessNews.postValue(listOf())
                    _sportsNews.postValue(listOf())
                    _technologyNews.postValue(listOf())
                    _scienceNews.postValue(listOf())
                    _healthNews.postValue(listOf())
                }
            }
        }
    }
}