package com.learning.newsapiclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.newsapiclient.data.util.Resource
import com.learning.newsapiclient.databinding.FragmentNewsBinding
import com.learning.newsapiclient.presentation.adapter.NewsAdapter
import com.learning.newsapiclient.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    // The purpose of overriding this function here we have to get ViewModel instance and this function always immediately
    // calls after all the views has been created.
    // it is much safer to use this function to avoid unexpected errors that can happen as result of partially created views.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            // this will allow us to get the selected news article instance. we are going to pass that article instance to
            // the info fragment using navigation arguments. To do that we have to make the Article instance Serializable.
            // after make it serialized we can write codes to get the selected article instance to a bundle and it to the
            // navigate function
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
            // as well as add the argument via navigation graph to receive this bundle into Info Fragment
        }
        initRecyclerView()
        viewNewsList()
        setSearchedView()
    }

    private fun initRecyclerView() {
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
    }

    private fun viewNewsList() {
        try {
            viewModel.getNewsHeadLines(country, page)
            viewModel.newsHeadLines.observe(viewLifecycleOwner) { response ->

//                Log.i("MYTAG", "" + response.data?.articles?.size)
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            newsAdapter.differ.submitList(it.articles.toList())
                            if (it.totalResults % 20 == 0) {
                                pages = it.totalResults / 20
                            } else {
                                pages = it.totalResults / 20 + 1
                            }
                            isLastPage = page == pages
                            /**
                             *  Above last condition (isLastPage = page == pages) defines, if current
                             *  page number equals to the number of pages that means list is in the last page.
                             */

                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let {
                            Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        } catch (E: Exception) {
            Toast.makeText(activity, "Some thing went wrong", Toast.LENGTH_LONG).show()
        }
    }

    private fun showProgressBar() {
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.GONE
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            // Using this layout manager instance we can get three properties of current recyclerview
            // 1.size of the current list
            // 2.visible items count
            // 3.starting position of the visible item count
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            /**
             * Before decide to do pagination or not , there are several facts to consider
             * there are several conditions to satisfy.
             * The current list has to reach to the last item before we do pagination.We
             * can check that using the topPosition, visibleItems and the sizeOfTheCurrentList
             */
            val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            // if these all conditions satisfy then if block execute
            if (shouldPaginate) {
                page++
                viewModel.getNewsHeadLines(country, page)
                isScrolling = false           // Because on going loading scroll is not possible
            }
        }
    }

    //search
    private fun setSearchedView() {
        fragmentNewsBinding.svNews.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchNews("us", query.toString(), page)
                    viewSearchedNews()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    MainScope().launch {
                        delay(2000)
                        viewModel.searchNews("us", newText.toString(), page)
                        viewSearchedNews()
                    }
                    return false
                }

            })

        fragmentNewsBinding.svNews.setOnCloseListener(object : SearchView.OnCloseListener,
            android.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                // here we need to initialize the recycler view
                initRecyclerView()
                viewNewsList()
                return false
            }

        })
    }

    fun viewSearchedNews() {
        try {
            viewModel.searchedNews.observe(viewLifecycleOwner) { response ->

//                Log.i("MYTAG", "" + response.data?.articles?.size)
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            newsAdapter.differ.submitList(it.articles.toList())
                            if (it.totalResults % 20 == 0) {
                                pages = it.totalResults / 20
                            } else {
                                pages = it.totalResults / 20 + 1
                            }
                            isLastPage = page == pages
                            /**
                             *  Above last condition (isLastPage = page == pages) defines, if current
                             *  page number equals to the number of pages that means list is in the last page.
                             */

                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let {
                            Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        } catch (E: Exception) {
            Toast.makeText(activity, "Some thing went wrong", Toast.LENGTH_LONG).show()
        }
    }

}

