package com.abhinav.pagingsample.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.abhinav.pagingsample.data.model.NewsItem

class NewsDataSourceFactory(private val query: String) : DataSource.Factory<Int, NewsItem>() {

    val sourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, NewsItem> {
        val source = NewsDataSource(query)
        sourceLiveData.postValue(source)
        return source
    }
}