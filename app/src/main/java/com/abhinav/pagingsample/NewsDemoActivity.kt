package com.abhinav.pagingsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.abhinav.pagingsample.data.model.NewsItem
import com.abhinav.pagingsample.ui.news.NewsAdapter
import com.abhinav.pagingsample.ui.news.NewsViewModel
import kotlinx.android.synthetic.main.activity_news_demo.*

class NewsDemoActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_demo)

        initViewModel()
        initAdapter()
        initSearch()
    }

    private fun initSearch() {
        et_news_query.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        et_news_query.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
        et_news_query.text.trim().let {
            if (it.isNotEmpty()) {
                rv_news.scrollToPosition(0)
                viewModel.searchNews(it)
                adapter.submitList(null)
            }
        }
    }

    private fun initAdapter() {
        adapter = NewsAdapter()
        rv_news.adapter = adapter
        rv_news.layoutManager = LinearLayoutManager(this)

        viewModel.newsSearchPagedData.observe(this, Observer<PagedList<NewsItem>> {
            //            showEmptyList(it?.size == 0)
            Log.e("Data", " size - ${it?.size}")
            adapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            Log.e("Toast", "${it?.status} ")
            if (!it?.msg.isNullOrBlank())
                Toast.makeText(baseContext, it?.msg, Toast.LENGTH_LONG).show()
        })
    }

    private fun showEmptyList(b: Boolean) {
        when (b) {
            true -> {
                emptyList.visibility = View.VISIBLE
                rv_news.visibility = View.GONE
            }

            false -> {
                emptyList.visibility = View.GONE
                rv_news.visibility = View.VISIBLE
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
    }

}
