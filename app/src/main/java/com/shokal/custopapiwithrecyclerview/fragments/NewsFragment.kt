package com.shokal.custopapiwithrecyclerview.fragments

import android.Manifest.permission.INTERNET
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.shokal.custopapiwithrecyclerview.R
import com.shokal.custopapiwithrecyclerview.adapter.NewsAdapter
import com.shokal.custopapiwithrecyclerview.databinding.FragmentNewsBinding
import com.shokal.custopapiwithrecyclerview.models.Article
import com.shokal.custopapiwithrecyclerview.networks.CheckNetworkConnection
import com.shokal.custopapiwithrecyclerview.viewmodels.NewsViewModel

class NewsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private val viewModel: NewsViewModel by viewModels()
    private val internetPermissionCode = 100
    private lateinit var internetIcon: ImageView
    private lateinit var checkNetworkConnection: CheckNetworkConnection
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.photos_grid)
        refreshLayout =view.findViewById(R.id.swipeLayout)
        recyclerView.setHasFixedSize(true)
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.setItemViewCacheSize(900)
        initializeAdapter()

        refreshLayout.setOnRefreshListener {
            initializeAdapter()
            refreshLayout.isRefreshing = false
        }

    }

    private fun initializeAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        if (internetPermission(requireContext())) {
            checkNetworkConnection = activity?.let { CheckNetworkConnection(it.application) }!!
            checkNetworkConnection.observe(viewLifecycleOwner) { isConnected ->
                if (isConnected) {
                    recyclerView.visibility = View.VISIBLE
                    observeData()
                } else {
                    recyclerView.visibility = View.GONE
//                    internetIcon.visibility = View.VISIBLE
                }
            }
        } else {
            takeInternetPermission()
        }
    }

    private fun takeInternetPermission() {
        checkPermission()
    }

    private fun internetPermission(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
        return (activeNetworkInfo != null) && activeNetworkInfo.isConnectedOrConnecting
    }

    private fun observeData() {
        viewModel.news.observe(viewLifecycleOwner) {
            recyclerView.adapter = NewsAdapter(
                requireContext(), viewModel, it as ArrayList<Article>
            )
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                INTERNET
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(INTERNET),
                internetPermissionCode
            )
        } else {
            Toast.makeText(requireContext(), "Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == internetPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Camera Permission Granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Camera Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}