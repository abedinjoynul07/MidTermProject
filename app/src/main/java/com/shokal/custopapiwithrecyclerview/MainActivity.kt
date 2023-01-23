package com.shokal.custopapiwithrecyclerview

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shokal.custopapiwithrecyclerview.adapter.NewsAdapter
import com.shokal.custopapiwithrecyclerview.databinding.ActivityMainBinding
import com.shokal.custopapiwithrecyclerview.workers.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    var adapter: NewsAdapter? = null
    private val internetPermissionCode = 100

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()

//        val handler = Handler()
//        val runnableCode: Runnable = object : Runnable {
//            override fun run() {
//                Toast.makeText(this@MainActivity, "Toast from handler", Toast.LENGTH_SHORT).show()
//                NewsFragment()
//                handler.postDelayed(this, 5000)
//            }
//        }
//        handler.post(runnableCode)

        setPeriodicWorkRequest()

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.bookMarkFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setPeriodicWorkRequest() {
        val workManager = WorkManager.getInstance(applicationContext)
//        val businessDataLoad = PeriodicWorkRequest
//            .Builder(LoadBusinessData::class.java, 15, TimeUnit.MINUTES)
//            .build()
//
//        val healthDataLoad = PeriodicWorkRequest
//            .Builder(LoadHealthData::class.java, 15, TimeUnit.MINUTES)
//            .build()
//
//        val newsDataLoad = PeriodicWorkRequest
//            .Builder(DataFetchWorker::class.java, 15, TimeUnit.MINUTES)
//            .build()
//
//        val scienceDataLoad = PeriodicWorkRequest
//            .Builder(LoadScienceData::class.java, 15, TimeUnit.MINUTES)
//            .build()
//
//        val sportsDataLoad = PeriodicWorkRequest
//            .Builder(LoadSportsData::class.java, 15, TimeUnit.MINUTES)
//            .build()

        val dataLoad =
            PeriodicWorkRequest.Builder(DataFetchWorker::class.java, 15, TimeUnit.MINUTES).build()
        workManager.enqueue(dataLoad)
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.INTERNET), internetPermissionCode
            )
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }
}