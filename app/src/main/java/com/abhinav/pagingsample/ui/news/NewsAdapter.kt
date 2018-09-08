package com.abhinav.pagingsample.ui.news

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.abhinav.pagingsample.data.model.NewsItem

class NewsAdapter : PagedListAdapter<NewsItem, NewsViewHolder>(REPO_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areItemsTheSame(oldItem: NewsItem?, newItem: NewsItem?): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NewsItem?, newItem: NewsItem?): Boolean {
                return oldItem?.title == newItem?.title
            }

        }
    }

}
