package com.shokal.custopapiwithrecyclerview.repositories

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shokal.custopapiwithrecyclerview.models.BookMarkNews
import com.shokal.custopapiwithrecyclerview.models.LocalArticle

@Dao
interface NewsDao {
    @Query("SELECT * FROM articles")
    fun getAll(): LiveData<List<LocalArticle>>

    @Query("SELECT * FROM bookmarks")
    fun getAllBookMark(): LiveData<List<BookMarkNews>>

    @Query("SELECT * FROM articles WHERE category = 'business'")
    fun getAllBusiness(): LiveData<List<LocalArticle>>

    @Query("SELECT * FROM articles WHERE category = 'sports'")
    fun getAllSports(): LiveData<List<LocalArticle>>

    @Query("SELECT * FROM articles WHERE category = 'technology'")
    fun getAllTechnology(): LiveData<List<LocalArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: LocalArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookMark(article: BookMarkNews)

    @Insert
    suspend fun insertAll(articles: List<LocalArticle>)

    @Update
    suspend fun update(article: LocalArticle)

    @Delete
    suspend fun delete(article: LocalArticle)

    @Query("DELETE FROM articles")
    suspend fun deleteAll()
}