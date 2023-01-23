package com.shokal.custopapiwithrecyclerview.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shokal.custopapiwithrecyclerview.fragments.NewsFragment
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class DataFetchWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        return try {
            NewsFragment().observeData()
//            BusinessFragment().observeData()
//            HealthFragment().observeData()
//            ScienceFragment().observeData()
//            SportsFragment().observeData()
//            TechnologyFragment().observeData()
            Timber.tag("worker").d("doWork: Success")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }


//    override suspend fun doWork(): Result {
//        return try {
//            scope.launch {
//                withContext(IO) {
//                    NewsFragment()
//                    BusinessFragment().observeData()
//                    HealthFragment().observeData()
//                    ScienceFragment().observeData()
//                    SportsFragment().observeData()
//                    TechnologyFragment().observeData()
//                }
//            }
//            Timber.tag("worker").d("doWork: Success")
//            Result.success()
//        } catch (e: Exception) {
//            Result.failure()
//        }
//    }

}