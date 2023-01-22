package com.shokal.custopapiwithrecyclerview

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shokal.custopapiwithrecyclerview.adapter.NewsAdapter
import com.shokal.custopapiwithrecyclerview.databinding.ActivityMainBinding
import com.shokal.custopapiwithrecyclerview.models.LocalArticle
import com.shokal.custopapiwithrecyclerview.viewmodels.LocalNewsViewModel
import timber.log.Timber
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: LocalNewsViewModel
    private lateinit var navController: NavController
    private var listArticles: ArrayList<LocalArticle> = ArrayList()
    var adapter: NewsAdapter? = null

    private val internetPermissionCode = 100

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()
        viewModel = ViewModelProvider(this)[LocalNewsViewModel::class.java]

        viewModel.newsList.observe(this) {
            listArticles.addAll(it)
        }
        adapter = NewsAdapter(this, viewModel, listArticles)
        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment2, R.id.bookMarkFragment2
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    override fun onBackPressed() {
//        val count = supportFragmentManager.backStackEntryCount
//        if (count > 0) {
//            super.onBackPressed()
//            //additional code
//        } else {
//            supportFragmentManager.popBackStack()
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.actionSearch)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.serach(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    //    private fun loadFragment(fragment: Fragment, removeFragment: Fragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.remove(removeFragment)
//        transaction.replace(R.id.nav_host_fragment, fragment)
//        transaction.commit()
//    }
//    private fun openFragment(fragment: Fragment) {
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
//        if (fragment.isAdded) {
//            transaction.show(fragment)
//        } else {
//            transaction.replace(R.id.nav_host_fragment_activity_main, fragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
//
//    }

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
            Timber.d("Permission Already Granted")
        }
    }
}

