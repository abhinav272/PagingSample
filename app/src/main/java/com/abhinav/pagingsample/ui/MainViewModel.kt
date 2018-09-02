package com.abhinav.pagingsample.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.abhinav.pagingsample.data.GithubRepository
import com.abhinav.pagingsample.data.model.RepoEntity
import com.abhinav.pagingsample.data.model.RepoSearchResult

class MainViewModel(private val repository: GithubRepository) : ViewModel() {
    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val repoResult: LiveData<RepoSearchResult> = Transformations.map(queryLiveData) {
        repository.searchQuery(it)
    }

    val repos: LiveData<List<RepoEntity>> = Transformations.switchMap(repoResult) { it -> it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult) { it -> it.networkErrors }

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.requestMore(immutableQuery)
            }
        }
    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value

}