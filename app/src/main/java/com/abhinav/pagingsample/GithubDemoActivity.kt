package com.abhinav.pagingsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.abhinav.pagingsample.data.model.RepoEntity
import com.abhinav.pagingsample.ui.github.MainViewModel
import com.abhinav.pagingsample.ui.github.ReposAdapter
import kotlinx.android.synthetic.main.activity_github_demo.*

class GithubDemoActivity : AppCompatActivity(), (RepoEntity, Int) -> Unit {
    override fun invoke(p1: RepoEntity, flag: Int) {
        when (flag) {
            0 -> viewModel.onDeleteClicked(p1)
            else -> viewModel.starRepo(p1)
        }
    }

    private lateinit var viewModel: MainViewModel
    private val adapter = ReposAdapter(this)

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_demo)

        initViewModel()
        setupDecorations()
//        setupScrollListner()
        initAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.searchRepo(query)
        initSearch(query)
    }

    private fun initSearch(query: String) {
        search_repo.setText(query)

        search_repo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        search_repo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
        search_repo.text.trim().let {
            if (it.isNotEmpty()) {
                list.scrollToPosition(0)
                viewModel.searchRepo(it.toString())
                adapter.submitList(null)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue())
    }

    private fun initAdapter() {
        list.adapter = adapter
        viewModel.repos.observe(this, Observer<PagedList<RepoEntity>> {
            Log.e("Data Github ", "${it?.size}")
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })

        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(this, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show()
        })

    }

    private fun showEmptyList(b: Boolean) {
        when (b) {
            true -> {
                emptyList.visibility = View.VISIBLE
                list.visibility = View.GONE
            }

            false -> {
                emptyList.visibility = View.GONE
                list.visibility = View.VISIBLE
            }
        }
    }

    private fun setupDecorations() {

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this,
                Injection.provideViewModelFactory(this)).get(MainViewModel::class.java)
    }
}
