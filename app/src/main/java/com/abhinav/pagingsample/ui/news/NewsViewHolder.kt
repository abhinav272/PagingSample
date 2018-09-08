package com.abhinav.pagingsample.ui.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.abhinav.pagingsample.R
import com.abhinav.pagingsample.data.model.NewsItem

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    private var tvAuthor: TextView = itemView.findViewById(R.id.tv_author)
    private var tvPublishedAt: TextView = itemView.findViewById(R.id.tv_published_at)
    private var ivItem: ImageView = itemView.findViewById(R.id.iv_news)


    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_view_item, parent, false)
            return NewsViewHolder(view)
        }
    }


    fun bind(newsItem: NewsItem?) {
        if (newsItem != null) {
            tvTitle.text = newsItem.title
            tvAuthor.text = newsItem.author
            tvPublishedAt.text = newsItem.publishedAt
        } else {
            Toast.makeText(itemView.context, "Loading next page", Toast.LENGTH_LONG).show()
        }
    }
}