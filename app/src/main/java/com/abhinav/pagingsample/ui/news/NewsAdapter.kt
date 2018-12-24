package com.abhinav.pagingsample.ui.news

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.abhinav.pagingsample.data.model.NewsItem

class NewsAdapter : PagedListAdapter<NewsItem, RecyclerView.ViewHolder>(REPO_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> PagingViewHolder.create(parent)
            else -> NewsViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        currentList?.size.let {
            if (it != null && it > 0) {
                when (holder) {
                    is NewsViewHolder -> holder.bind(getItem(position))
                }
            }
        }


    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            currentList?.size -> 0
            else -> 1
        }
    }

    override fun getItemCount(): Int {
        return when (super.getItemCount()) {
            0 -> 0
            else -> super.getItemCount() + 1
        }
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
