package com.abhinav.pagingsample

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import com.abhinav.pagingsample.ui.news.NewsAdapter
import com.abhinav.pagingsample.ui.news.NewsViewModel
import kotlinx.android.synthetic.main.activity_news_demo.*

class NewsDemoActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
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
    }

    private fun updateRepoListFromInput() {

    }

    private fun initAdapter() {
        adapter = NewsAdapter()
        rv_news.adapter = adapter
        rv_news.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
    }

}
