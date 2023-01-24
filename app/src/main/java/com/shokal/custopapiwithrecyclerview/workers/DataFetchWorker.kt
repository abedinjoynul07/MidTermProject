package com.shokal.custopapiwithrecyclerview.workers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shokal.custopapiwithrecyclerview.BuildConfig
import com.shokal.custopapiwithrecyclerview.fragments.autoReload
import com.shokal.custopapiwithrecyclerview.models.Article
import com.shokal.custopapiwithrecyclerview.models.LocalArticle
import com.shokal.custopapiwithrecyclerview.repositories.NewsRepository
import com.shokal.custopapiwithrecyclerview.services.NewsApi
import kotlinx.coroutines.*
import timber.log.Timber

class DataFetchWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Timber.tag("worker").d("Worker Started")
        return try {
            autoReload()
            Timber.tag("worker").d("Get Article Called ")
            Result.success()
        } catch (e: Exception) {
            Timber.tag("worker").d(e)
            Result.failure()
        }
    }
}

