package com.abhinav.pagingsample.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repos")
data class RepoEntity(
        @PrimaryKey
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("name") val name: String,
        @field:SerializedName("full_name") val fullName: String,
        @field:SerializedName("description") val description: String?,
        @field:SerializedName("html_url") val url: String,
        @field:SerializedName("stargazers_count") var stars: Int,
        @field:SerializedName("forks_count") val forks: Int,
        @field:SerializedName("language") val language: String?
)
