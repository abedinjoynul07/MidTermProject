package com.shokal.custopapiwithrecyclerview.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.shokal.custopapiwithrecyclerview.MainActivity
import com.shokal.custopapiwithrecyclerview.R
import com.shokal.custopapiwithrecyclerview.adapter.TabAdapter
import com.shokal.custopapiwithrecyclerview.adapter.TabAdapter.Companion.tabList
import com.shokal.custopapiwithrecyclerview.databinding.FragmentHomeBinding
import com.shokal.custopapiwithrecyclerview.viewmodels.NewsViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewmode: NewsViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.tabLayoutHome
        val viewPage = binding.viewPager2
        val tabAdapter = TabAdapter(childFragmentManager, lifecycle)
        viewPage.adapter = tabAdapter
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = tabList[position].title
        }.attach()
    }
}