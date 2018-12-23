package com.abhinav.pagingsample.ui.news

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.abhinav.pagingsample.data.NewsRepository
import com.abhinav.pagingsample.data.model.NetworkState

class NewsViewModel : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val newsSearchResult = Transformations.map(queryLiveData) {
        NewsRepository().searchNews(it)
    }

    val newsSearchPagedData = Transformations.switchMap(newsSearchResult) {
        it.data
    }

    val networkState = Transformations.switchMap(newsSearchResult) { input ->
        input.networkErrors
    }

    fun searchNews(query: CharSequence) {
        queryLiveData.value = query.toString()
    }

}
