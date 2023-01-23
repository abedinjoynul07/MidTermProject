package com.shokal.custopapiwithrecyclerview.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shokal.custopapiwithrecyclerview.fragments.TechnologyFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoadTechnologyData(context: Context, params: WorkerParameters) : Worker(context, params) {
    @OptIn(DelicateCoroutinesApi::class)
    override fun doWork(): Result {
        return try {
            GlobalScope.launch {
                TechnologyFragment().observeData()
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}