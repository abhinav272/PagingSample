package com.abhinav.pagingsample

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.abhinav.pagingsample.data.GithubRepository
import com.abhinav.pagingsample.data.RepoDatabase
import com.abhinav.pagingsample.data.api.GithubService
import com.abhinav.pagingsample.ui.github.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [GithubLocalCache] based on the database DAO.
     */
//    private fun provideCache(context: Context): GithubLocalCache {
//        val database = RepoDatabase.getInstance(context)
//        return GithubLocalCache(database.reposDao(), Executors.newSingleThreadExecutor())
//    }

    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(GithubService.create(), RepoDatabase.getInstance(context.applicationContext).reposDao())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }

}