package com.abhinav.pagingsample.data.model

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class NewsSearchResult(val data: LiveData<PagedList<NewsItem>>,
                            val networkErrors: LiveData<NetworkState>)
