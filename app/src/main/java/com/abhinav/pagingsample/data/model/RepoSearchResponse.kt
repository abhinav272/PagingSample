package com.abhinav.pagingsample.data.model

import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
        @SerializedName("total_count") val total: Int = 0,
        @SerializedName("items") val items: List<RepoEntity> = emptyList(),
        val nextPage: Int? = null
)
