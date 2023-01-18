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
class NewsViewModel(application: Application): AndroidViewModel(application) {
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status
    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> = _news
    val context : Context

    init {
        context = application.applicationContext
        getNews()
    }
    private fun getNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _news.value = NewsApi.retrofitService.getNews(BuildConfig.API_KEY, "apple").articles
                _status.value = NewsApiStatus.DONE
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                _status.value = NewsApiStatus.ERROR
                _news.value = listOf()
            }
        }
    }
}