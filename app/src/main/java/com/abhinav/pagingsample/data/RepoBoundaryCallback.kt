package com.abhinav.pagingsample.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.abhinav.pagingsample.data.api.GithubService
import com.abhinav.pagingsample.data.api.searchRepos
import com.abhinav.pagingsample.data.model.NetworkState
import com.abhinav.pagingsample.data.model.RepoEntity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class RepoBoundaryCallback(private val query: String,
                           private val service: GithubService,
                           private val repoDao: RepoDao) : PagedList.BoundaryCallback<RepoEntity>() {


    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    // keep the last requested page.
// When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    private val _pagingLiveData = MutableLiveData<NetworkState>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors
    val pagingLiveData: LiveData<NetworkState>
        get() = _pagingLiveData

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: RepoEntity) {
        requestAndSaveData(query)
        _pagingLiveData.value = NetworkState.LOADED
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->
            lastRequestedPage++
            isRequestInProgress = false
            _pagingLiveData.value = NetworkState.PAGING
            Completable.fromAction { repoDao.insert(repos) }.subscribeOn(Schedulers.io()).subscribe()
        }, { error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
            _pagingLiveData.value = NetworkState.FAILED
        })
    }
}