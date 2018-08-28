package com.abhinav.pagingsample.data

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.abhinav.pagingsample.data.api.GithubService
import com.abhinav.pagingsample.data.api.searchRepos
import com.abhinav.pagingsample.data.model.RepoSearchResult

class GithubRepository(private val service: GithubService,
                       private val repoDao: RepoDao) {

    private var lastRequestedPage = 1
    private val networkErrors = MutableLiveData<String>()
    private var isRequestInProgress = false

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }


    fun searchQuery(query: String): RepoSearchResult {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1
        requestAndSaveData(query)

        // Get data from the local cache
        val data = repoDao.fetchRepo(query)

        return RepoSearchResult(data, networkErrors)
    }

    fun requestMore(query: String) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->
            lastRequestedPage++
            isRequestInProgress = false
            repoDao.insert(repos)
        }, { error ->
            networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }
}