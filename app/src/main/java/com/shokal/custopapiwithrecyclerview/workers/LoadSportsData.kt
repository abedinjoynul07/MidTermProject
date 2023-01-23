package com.shokal.custopapiwithrecyclerview.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shokal.custopapiwithrecyclerview.fragments.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoadSportsData(context: Context, params: WorkerParameters) : Worker(context, params) {
    @OptIn(DelicateCoroutinesApi::class)
    override fun doWork(): Result {
        return try {
            GlobalScope.launch {
                SportsFragment().observeData()
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}