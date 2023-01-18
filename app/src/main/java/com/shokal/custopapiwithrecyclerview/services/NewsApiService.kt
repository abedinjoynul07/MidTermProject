package com.shokal.custopapiwithrecyclerview.services

import com.shokal.custopapiwithrecyclerview.models.News
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"

val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @GET("everything")
    suspend fun getAllNews(
        @Query("apiKey") apiKey: String,
        @Query("q") q: String,
    ) : News

    @GET("top-headlines")
    suspend fun getBusinessNews(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String,
    ) : News

    @GET("top-headlines")
    suspend fun getSportsNews(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String,
    ) : News

    @GET("top-headlines")
    suspend fun getTechnologyNews(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String,
    ) : News
}

object NewsApi{
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}