package com.abhinav.pagingsample.data.model

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    PAGING
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status,
        val msg: String? = null) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        val FAILED = NetworkState(Status.FAILED)
        val PAGING = NetworkState(Status.PAGING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}