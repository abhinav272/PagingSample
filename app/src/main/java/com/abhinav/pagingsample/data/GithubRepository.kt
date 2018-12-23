package com.abhinav.pagingsample.data

import android.arch.paging.LivePagedListBuilder
import android.util.Log
import com.abhinav.pagingsample.data.api.GithubService
import com.abhinav.pagingsample.data.model.RepoSearchResult

class GithubRepository(private val service: GithubService,
                       private val repoDao: RepoDao) {

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }


    fun searchQuery(query: String): RepoSearchResult {
        Log.d("GithubRepository", "New query: $query")

//         Get data from the local cache
//        val data = repoDao.fetchRepo(query)
        val dataSourceFactory = repoDao.fetchRepo(query)
        // Construct the boundary callback
        val boundaryCallback = RepoBoundaryCallback(query, service, repoDao)
        val networkErrors = boundaryCallback.networkErrors
        val pagingLiveData = boundaryCallback.pagingLiveData

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()

        // Get the network errors exposed by the boundary callback
        return RepoSearchResult(data, networkErrors)
    }
}