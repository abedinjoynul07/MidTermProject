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
    var bookMarkNews: LiveData<List<BookMarkNews>>

    //    var businesesNewsList: LiveData<List<LocalArticle>>
//    var sportsNewsList: LiveData<List<LocalArticle>>
//    var technologyNewsList: LiveData<List<LocalArticle>>
//    var scienceNewsList: LiveData<List<LocalArticle>>
//    var healthNewsList: LiveData<List<LocalArticle>>
    var newsRepo: NewsRepository

    init {
        newsRepo = NewsRepository(application)
        bookMarkNews = newsRepo.getAllBookMarkNews()
//        businesesNewsList = newsRepo.getBusinessNews()
//        sportsNewsList = newsRepo.getSportsNews()
//        technologyNewsList = newsRepo.getTechnologyNews()
//        scienceNewsList = newsRepo.getScienceNews()
//        healthNewsList = newsRepo.getHealthNews()
    }

    fun getNews(): LiveData<List<LocalArticle>> {
        return newsRepo.getAllNews()
    }

    fun getBusinessNews(): LiveData<List<LocalArticle>> {
        return newsRepo.getBusinessNews()
    }

    fun getSportsNews(): LiveData<List<LocalArticle>> {
        return newsRepo.getSportsNews()
    }

    fun getTechnologyNews(): LiveData<List<LocalArticle>> {
        return newsRepo.getTechnologyNews()
    }

    fun getScienceNews(): LiveData<List<LocalArticle>> {
        return newsRepo.getScienceNews()
    }

    fun getHealthNews(): LiveData<List<LocalArticle>> {
        return newsRepo.getHealthNews()
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

    fun deleteBookBarkArticle(bookMarkNews: BookMarkNews) {
        viewModelScope.launch {
            newsRepo.deleteBookMarKArticle(bookMarkNews)
        }
    }

    fun deleteAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.deleteAllNews()
        }
    }

    fun updateArticle(article: LocalArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.updateArticle(article)
        }
    }
}