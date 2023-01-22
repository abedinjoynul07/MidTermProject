package com.shokal.custopapiwithrecyclerview.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "articles")
@Parcelize
data class LocalArticle(
    val id: Int,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val category: String,
    val isFavourite: Boolean
) : Parcelable
