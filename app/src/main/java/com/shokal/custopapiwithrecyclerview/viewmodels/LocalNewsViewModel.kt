package com.shokal.custopapiwithrecyclerview.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.shokal.custopapiwithrecyclerview.models.BookMarkNews
import com.shokal.custopapiwithrecyclerview.models.LocalArticle
import com.shokal.custopapiwithrecyclerview.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalNewsViewModel(application: Application) : AndroidViewModel(application) {
    var newsList: LiveData<List<LocalArticle>>
    var bookMarkNews: LiveData<List<BookMarkNews>>
    var businesesNewsList: LiveData<List<LocalArticle>>
    var sportsNewsList: LiveData<List<LocalArticle>>
    var technologyNewsList: LiveData<List<LocalArticle>>
    var newsRepo: NewsRepository

    init {
        newsRepo = NewsRepository(application)
        newsList = newsRepo.getAllNews()
        bookMarkNews = newsRepo.getAllBookMarkNews()
        businesesNewsList = newsRepo.getBusinessNews()
        sportsNewsList = newsRepo.getSportsNews()
        technologyNewsList = newsRepo.getTechnologyNews()
    }

    fun addBookMarkArticle(article: BookMarkNews) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.insertBookMarkArticle(article)
        }
    }

    fun addAllArticle(article: List<LocalArticle>) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.insertAllArticle(article)
        }
    }
}