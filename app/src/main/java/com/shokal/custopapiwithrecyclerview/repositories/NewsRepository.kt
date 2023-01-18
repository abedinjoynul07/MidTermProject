package com.shokal.custopapiwithrecyclerview.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.shokal.custopapiwithrecyclerview.models.LocalArticle

class NewsRepository(context: Context) {
    private val db: NewsDao = DataBase.getDatabase(context).newsDao()

    private val localContext = context    //Fetch All the Users
    fun getAllUsers(): LiveData<List<LocalArticle>> {
        return db.getAll()
    }

    // Insert new user
    suspend fun insertArticle(article: LocalArticle) {
        return db.insert(article)
    }

    // Deleted all user
    suspend fun deleteAllUser() {
        db.deleteAll()
    }

    // Delete user
    suspend fun deleteUser(article: LocalArticle) {
        db.delete(article)
    }


    //Update User
    suspend fun updateUser(article: LocalArticle) {
        db.update(article)
    }
}