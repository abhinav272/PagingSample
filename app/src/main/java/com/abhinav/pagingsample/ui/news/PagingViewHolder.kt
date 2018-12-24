package com.abhinav.pagingsample.ui.news

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhinav.pagingsample.R

class PagingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): PagingViewHolder {
            Log.e("null","received")
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_single_item_paging_loader, parent, false)
            return PagingViewHolder(view)
        }
    }
}
