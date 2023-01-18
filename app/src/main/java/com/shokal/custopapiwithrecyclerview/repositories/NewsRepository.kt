package com.shokal.custopapiwithrecyclerview.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.shokal.custopapiwithrecyclerview.models.Article
import com.shokal.custopapiwithrecyclerview.models.LocalArticle

class NewsRepository(context: Context) {
    private val db: NewsDao = DataBase.getDatabase(context).newsDao()

    private val localContext = context    //Fetch All the Users
    fun getAllNews(): LiveData<List<LocalArticle>> {
        return db.getAll()
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

    // Insert new user
    suspend fun insertArticle(article: LocalArticle) {
        return db.insert(article)
    }


    suspend fun insertAllArticle(article: List<LocalArticle>) {
        return db.insertAll(article)
    }
    // Delete user
    suspend fun deleteUser(article: LocalArticle) {
        db.delete(article)
    }

}