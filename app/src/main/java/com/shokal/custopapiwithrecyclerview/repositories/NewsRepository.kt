package com.shokal.custopapiwithrecyclerview.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.shokal.custopapiwithrecyclerview.models.BookMarkNews
import com.shokal.custopapiwithrecyclerview.models.LocalArticle

class NewsRepository(context: Context) {
    private val db: NewsDao = DataBase.getDatabase(context).newsDao()
    private val localContext = context    //Fetch All the Users
    fun getAllNews(): LiveData<List<LocalArticle>> {
        return db.getAll()
    }

    fun getAllBookMarkNews(): LiveData<List<BookMarkNews>> {
        return db.getAllBookMark()
    }

    fun getBusinessNews(): LiveData<List<LocalArticle>> {
        return db.getAllBusiness()
    }

    fun getSportsNews(): LiveData<List<LocalArticle>> {
        return db.getAllSports()
    }

    fun getTechnologyNews(): LiveData<List<LocalArticle>> {
        return db.getAllTechnology()
    }

    fun getScienceNews(): LiveData<List<LocalArticle>> {
        return db.getAllScience()
    }

    fun getHealthNews(): LiveData<List<LocalArticle>> {
        return db.getAllHealth()
    }

    suspend fun insertBookMarkArticle(article: BookMarkNews) {
        return db.insertBookMark(article)
    }

    suspend fun insertAllArticle(article: List<LocalArticle>) {
        return db.insertAll(article)
    }

    suspend fun updateArticle(article: LocalArticle) {
        db.update(article)
    }

    // Delete Articles
    suspend fun deleteAllNews() {
        db.deleteAll()
    }

    suspend fun deleteBookMarKArticle(bookMarkNews: BookMarkNews) {
        db.deleteBookMarKArticle(bookMarkNews)
    }
}