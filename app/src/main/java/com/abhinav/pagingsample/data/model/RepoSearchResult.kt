package com.abhinav.pagingsample.data.model

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class RepoSearchResult(
        val data: LiveData<PagedList<RepoEntity>>,
        val networkErrors: LiveData<String>
)