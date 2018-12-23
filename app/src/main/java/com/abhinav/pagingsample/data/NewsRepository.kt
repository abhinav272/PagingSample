package com.abhinav.pagingsample.data

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
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

        val config: PagedList.Config = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(2)
                .setPageSize(NETWORK_PAGE_SIZE)
                .build()

        val livePagedList = LivePagedListBuilder(dataSourceFactory, config)
                .setInitialLoadKey(1)
                .build()

        return NewsSearchResult(livePagedList, networkErrorsLiveData)

    }

}
