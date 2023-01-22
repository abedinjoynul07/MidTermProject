package com.shokal.custopapiwithrecyclerview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.shokal.custopapiwithrecyclerview.R
import com.shokal.custopapiwithrecyclerview.adapter.BookMarkAdapter
import com.shokal.custopapiwithrecyclerview.databinding.FragmentBookMarkBinding
import com.shokal.custopapiwithrecyclerview.models.BookMarkNews
import com.shokal.custopapiwithrecyclerview.viewmodels.LocalNewsViewModel

class BookMarkFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private val viewModel: LocalNewsViewModel by viewModels()
    private var _binding: FragmentBookMarkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookMarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onResume() {
        super.onResume()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.photos_grid)
        refreshLayout = view.findViewById(R.id.swipeLayout)
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
        recyclerView.visibility = View.VISIBLE
        observeData()
    }

    private fun observeData() {
        viewModel.bookMarkNews.observe(viewLifecycleOwner) {
            recyclerView.adapter = BookMarkAdapter(
                requireContext(), viewModel, it as ArrayList<BookMarkNews>
            )
        }
    }
}