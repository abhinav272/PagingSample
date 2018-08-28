package com.abhinav.pagingsample.data.model

import android.arch.lifecycle.LiveData

data class RepoSearchResult(
        val data: LiveData<List<RepoEntity>>,
        val networkErrors: LiveData<String>
)