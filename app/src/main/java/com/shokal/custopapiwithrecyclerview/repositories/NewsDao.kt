package com.shokal.custopapiwithrecyclerview.repositories

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shokal.custopapiwithrecyclerview.models.LocalArticle

@Dao
interface NewsDao {
    @Query("SELECT * FROM articles")
    fun getAll(): LiveData<List<LocalArticle>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: LocalArticle)

    @Update
    suspend fun update(article: LocalArticle)

    @Delete
    suspend fun delete(article: LocalArticle)

    @Query("DELETE FROM articles")
    suspend fun deleteAll()
}