package com.abhinav.pagingsample.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.abhinav.pagingsample.data.api.searchNews
import com.abhinav.pagingsample.data.model.NetworkState
import com.abhinav.pagingsample.data.model.NewsItem

class NewsDataSource(private val query: String) : PageKeyedDataSource<Int, NewsItem>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NewsItem>) {
        networkState.postValue(NetworkState.LOADING)

        searchNews(query, 1, { newsItems ->
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(newsItems, 0, 2)
//            callback.onResult(newsItems, 0, 20, 0, 2)
        }, { error ->
            networkState.postValue(NetworkState.error(error))
        })

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsItem>) {
        networkState.postValue(NetworkState.LOADING)

        searchNews(query, params.key, { newsItems ->
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(newsItems, params.key + 1)
        }, { error ->
            networkState.postValue(NetworkState.error(error))
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsItem>) {

    }

}
