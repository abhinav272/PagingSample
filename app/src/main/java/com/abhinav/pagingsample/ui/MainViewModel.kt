package com.abhinav.pagingsample.ui

import android.arch.lifecycle.ViewModel
import com.abhinav.pagingsample.data.GithubRepository

class MainViewModel(repository: GithubRepository) : ViewModel() {
    fun searchRepo(query: String) {

    }

    fun lastQueryValue(): String? {
        return null
    }

}
