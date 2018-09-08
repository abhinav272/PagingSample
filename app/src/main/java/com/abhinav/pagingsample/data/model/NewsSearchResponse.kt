package com.abhinav.pagingsample.data.model

import com.google.gson.annotations.SerializedName

data class NewsSearchResponse(
        @SerializedName("totalResults") var totalResults: Int,
        @SerializedName("status") var status: String?,
        @SerializedName("articles") var articles: List<NewsItem>?
)