package com.androiddevs.mvvmnewsapp.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.androiddevs.mvvmnewsapp.util.Constants
import com.androiddevs.mvvmnewsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_breaking_news.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_search_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG= "an error occured "
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)







        viewModel=(activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,bundle)
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response)
            {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{
                        // we need to us a list not mutable list that's why we added toList() it's a bug
                        newsResponse ->  newsAdapter.differ.submitList(newsResponse.articles.toList())
                        //pagination
                        val totalPages= newsResponse.totalResults /QUERY_PAGE_SIZE +2
                        // we reached the last page of the first list of articles so we need to load other articles
                        isLastPage=viewModel.breakingNewsPage == totalPages
                        if(isLastPage)
                        {
                            rvBreakingNews.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let{message ->
                       Toast.makeText(activity,"an Error occured :$message",Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading ->
                {
                    showProgressBar()
                }
            }
        })





        //pull to refresh
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity as NewsActivity, R.color.colorPrimary))
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        itemsswipetorefresh.setOnRefreshListener {
            viewModel.getBreakingNews("us")
           Toast.makeText(activity,"refresh",Toast.LENGTH_SHORT).show()
            itemsswipetorefresh.isRefreshing = false
        }
    }


    //handling pagination
    var isLoading =false
    var isLastPage = false
    var isScrolling =false

    val scrollListener = object: RecyclerView.OnScrollListener() {
        // ctrl + o
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            {
                isScrolling= true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager= recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition =layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount =layoutManager.childCount
            val totalItemCount =layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning =firstVisibleItemPosition >=0
            val isTotalMoreThanVisible =totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate= isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate)
            {
                viewModel.getBreakingNews("us")
                isScrolling =false
            }
            else
            {
                rvBreakingNews.setPadding(0,0,0,0)
            }
        }
    }

    private fun  setupRecyclerView()
    {
        newsAdapter= NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager= LinearLayoutManager(activity)

            //pagination
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    private fun hideProgressBar()
    {
        paginationProgressBar.visibility= View.INVISIBLE
        isLoading=false

    }

    private fun showProgressBar()
    {
        paginationProgressBar.visibility= View.VISIBLE
        isLoading=true
    }

}