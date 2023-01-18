package com.shokal.custopapiwithrecyclerview.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.shokal.custopapiwithrecyclerview.models.LocalArticle
import com.shokal.custopapiwithrecyclerview.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalNewsViewModel(application: Application) : AndroidViewModel(application) {
    var newsList: LiveData<List<LocalArticle>>
    var newsRepo: NewsRepository

    init {
        newsRepo = NewsRepository(application)
        newsList = newsRepo.getAllUsers()
    }

    fun addArticle(article: LocalArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.insertArticle(article)
        }
    }

    fun deleteUser(article: LocalArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.deleteUser(article)
        }
    }

    fun deleteAllUser() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.deleteAllUser()
        }
    }

    fun updateUser(article: LocalArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.updateUser(article)
        }
    }
}