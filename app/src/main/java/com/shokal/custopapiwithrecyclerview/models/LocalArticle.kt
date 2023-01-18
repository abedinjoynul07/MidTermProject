package com.shokal.custopapiwithrecyclerview.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class LocalArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String?,
    val category: String,
    val favourite: Int,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String
)
