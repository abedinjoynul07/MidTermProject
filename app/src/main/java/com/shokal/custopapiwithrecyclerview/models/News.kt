package com.shokal.custopapiwithrecyclerview.models

data class News(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int?
)