package com.shokal.custopapiwithrecyclerview.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.shokal.custopapiwithrecyclerview.R
import com.shokal.custopapiwithrecyclerview.adapter.NewsAdapter
import com.shokal.custopapiwithrecyclerview.databinding.FragmentNewsBinding
import com.shokal.custopapiwithrecyclerview.models.LocalArticle
import com.shokal.custopapiwithrecyclerview.viewmodels.LocalNewsViewModel
import com.shokal.custopapiwithrecyclerview.viewmodels.NewsViewModel
import kotlinx.coroutines.Job


lateinit var viewModel: LocalNewsViewModel
lateinit var apiViewModel: NewsViewModel

class NewsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private var allEqual = false
    private val job = Job()
    private val result = mutableListOf<LocalArticle>()
    private lateinit var progressBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        progressBar = ProgressDialog(requireContext())
        progressBar.setCancelable(false)
        progressBar.setMessage("Fetching Data...")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[LocalNewsViewModel::class.java]
        apiViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_item, menu)
        val item = menu.findItem(R.id.actionSearch)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val adapter = recyclerView.adapter as NewsAdapter
                    adapter.filter(newText)
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job.cancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.photos_grid)
        refreshLayout = view.findViewById(R.id.swipeLayout)
        recyclerView.setHasFixedSize(true)
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.setItemViewCacheSize(900)

        progressBar.show()
        initializeAdapter()
        progressBar.dismiss()

        refreshLayout.setOnRefreshListener {
            progressBar.show()
            initializeAdapter()
            progressBar.dismiss()
            refreshLayout.isRefreshing = false
        }

    }

    fun initializeAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.getNews().observe(viewLifecycleOwner) {
            if (it.size > 0) {
                loadData()
            } else {
                observeData()
            }
        }
    }

    fun observeData() {
        Toast.makeText(requireContext(), "Called Api", Toast.LENGTH_SHORT).show()
        apiViewModel.news.observe(viewLifecycleOwner) { articles ->
            articles.map {
                LocalArticle(
                    it.author,
                    it.content,
                    it.description,
                    it.publishedAt,
                    it.title,
                    it.url,
                    it.urlToImage,
                    "general",
                    false
                ).let { it2 ->
                    result.add(
                        it2
                    )
                }
            }
            viewModel.getNews().observe(viewLifecycleOwner) { articles ->
                articles.map { localNews ->
                    apiViewModel.news.observe(viewLifecycleOwner) { apiArticles ->
                        apiArticles.map {
                            if (localNews.url == it.url) {
                                allEqual = true
                            } else {
                                allEqual
                            }
                        }
                    }
                }
            }
            if (!allEqual) {
                viewModel.addAllArticle(result)
            } else {
                Toast.makeText(requireContext(), "Up to dated...", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getNews().observe(viewLifecycleOwner) {
            recyclerView.adapter = NewsAdapter(
                requireContext(), viewModel, it as ArrayList<LocalArticle>
            )
        }
    }

    private fun loadData() {
        viewModel.getNews().observe(viewLifecycleOwner) {
            recyclerView.adapter = NewsAdapter(
                requireContext(), viewModel, it as ArrayList<LocalArticle>
            )
        }
    }
}


fun autoReload() {
    apiViewModel.getNews()
}