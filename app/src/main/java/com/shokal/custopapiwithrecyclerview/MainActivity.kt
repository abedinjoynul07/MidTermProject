package com.shokal.custopapiwithrecyclerview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shokal.custopapiwithrecyclerview.adapter.NewsAdapter
import com.shokal.custopapiwithrecyclerview.databinding.ActivityMainBinding
import com.shokal.custopapiwithrecyclerview.fragments.BookMarkFragment
import com.shokal.custopapiwithrecyclerview.fragments.HomeFragment
import com.shokal.custopapiwithrecyclerview.models.LocalArticle
import com.shokal.custopapiwithrecyclerview.viewmodels.LocalNewsViewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: LocalNewsViewModel
    private var listTasks: List<LocalArticle> = ArrayList()

    private val internetPermissionCode = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LocalNewsViewModel::class.java]

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bookMarkFragment -> loadFragment(BookMarkFragment(), HomeFragment())
                R.id.actionSearch -> {
                    val searchItem: MenuItem = it
                    val searchView: SearchView = searchItem.actionView as SearchView
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                        android.widget.SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(msg: String): Boolean {
                            filter(msg)
                            return false
                        }
                    })
                }
                else -> loadFragment(HomeFragment(), BookMarkFragment())
            }
            true
        }
    }

    private fun filter(text: String) {
        val filteredlist: MutableList<LocalArticle> = ArrayList()
        for (item in listTasks) {
            if (item.title?.lowercase()?.contains(text.lowercase()) == true) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            NewsAdapter(this, viewModel, filteredlist as ArrayList<LocalArticle>)
        }
    }

    private fun loadFragment(fragment: Fragment, removeFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.remove(removeFragment)
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET),
                internetPermissionCode
            )
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
            Timber.d("Permission Already Granted")
        }
    }

}