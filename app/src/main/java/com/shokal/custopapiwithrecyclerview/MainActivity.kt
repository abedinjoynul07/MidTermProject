package com.shokal.custopapiwithrecyclerview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.shokal.custopapiwithrecyclerview.databinding.ActivityMainBinding
import com.shokal.custopapiwithrecyclerview.fragments.BookMarkFragment
import com.shokal.custopapiwithrecyclerview.fragments.HomeFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val internetPermissionCode = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bookMarkFragment -> loadFragment(BookMarkFragment())
                else -> {
                    loadFragment(HomeFragment())
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
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