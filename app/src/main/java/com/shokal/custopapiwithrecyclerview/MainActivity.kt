package com.shokal.custopapiwithrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.Orientation
import com.shokal.custopapiwithrecyclerview.adapter.ViewPagerAdapter
import com.shokal.custopapiwithrecyclerview.databinding.ActivityMainBinding
import com.shokal.custopapiwithrecyclerview.fragments.BookMarkFragment
import com.shokal.custopapiwithrecyclerview.fragments.NewsFragment

class MainActivity : AppCompatActivity(), ViewPagerAdapter.ConditionViewPager {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var fragmentList : List<String> = listOf("Home", "Sports", "Technology")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.homeFragment-> {
                loadFragment(NewsFragment())
                return true
            }
            R.id.bookMarkFragment -> {
                loadFragment(BookMarkFragment())
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }

    private fun setupViewPager() {
        val viewPager = binding.viewPagerHost

        viewPager.adapter = ViewPagerAdapter(fragmentList, this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun condition(position: Int, fullSize: Int) {
        Toast.makeText(this, "$position / $fullSize", Toast.LENGTH_SHORT).show()

    }

}