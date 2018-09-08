package com.abhinav.pagingsample.ui.github

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.abhinav.pagingsample.data.model.RepoEntity

class ReposAdapter : PagedListAdapter<RepoEntity, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = RepoViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as RepoViewHolder).bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<RepoEntity>() {
            override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean =
                    oldItem.fullName == newItem.fullName

            override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean =
                    oldItem == newItem
        }
    }
}
