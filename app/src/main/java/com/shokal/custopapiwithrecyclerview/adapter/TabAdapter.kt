package com.shokal.custopapiwithrecyclerview.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shokal.custopapiwithrecyclerview.fragments.BusinessFragment
import com.shokal.custopapiwithrecyclerview.fragments.NewsFragment
import com.shokal.custopapiwithrecyclerview.fragments.SportsFragment
import com.shokal.custopapiwithrecyclerview.fragments.TechnologyFragment
import com.shokal.custopapiwithrecyclerview.models.Tab

class TabAdapter(manager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(manager, lifecycle){
    companion object {
        val tabList = listOf(
            Tab(NewsFragment(), "All News"),
            Tab(BusinessFragment(), "Business"),
            Tab(SportsFragment(), "Sports"),
            Tab(TechnologyFragment(), "Technology")
        )
    }

    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabList[position].fragment
    }
}