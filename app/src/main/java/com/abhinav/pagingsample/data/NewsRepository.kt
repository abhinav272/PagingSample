package com.abhinav.pagingsample.data

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.util.Log
import com.abhinav.pagingsample.data.model.NewsSearchResult

class NewsRepository {

    companion object {
        private val NETWORK_PAGE_SIZE = 20
    }

    fun searchNews(query: String): NewsSearchResult {
        Log.d("NewsRepository", "New query: $query")

        val dataSourceFactory = NewsDataSourceFactory(query)

        val networkErrorsLiveData = Transformations.switchMap(dataSourceFactory.sourceLiveData) { input ->
            input.networkState
        }

        val livePagedList = LivePagedListBuilder(dataSourceFactory, NETWORK_PAGE_SIZE)
                .setInitialLoadKey(1)
                .build()

        return NewsSearchResult(livePagedList, networkErrorsLiveData)

    }

}
