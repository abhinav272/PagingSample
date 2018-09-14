package com.abhinav.pagingsample.data.api

import android.util.Log
import com.abhinav.pagingsample.BuildConfig
import com.abhinav.pagingsample.data.model.NewsItem
import com.abhinav.pagingsample.data.model.NewsSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

fun searchNews(query: String, page: Int,  onSuccess: (newsItems: List<NewsItem>) -> Unit,
               onError: (error: String) -> Unit) {

    NewsService.create().searchNewsForQuery(query, page).enqueue(object : Callback<NewsSearchResponse> {
        override fun onFailure(call: Call<NewsSearchResponse>, t: Throwable) {
            Log.e("NewsService: ", t.message)
            onError(t.message ?: "unknown error")
        }

        override fun onResponse(call: Call<NewsSearchResponse>, response: Response<NewsSearchResponse>) {
            if (response.isSuccessful
                    && response.body() != null
                    && response.body()!!.status.equals("ok")) {
                response.body()!!.articles?.let { onSuccess(it) }
            } else {
                onError(response.errorBody()?.string() ?: "Unknown error")
            }
        }
    })
}


interface NewsService {

    @GET("/v2/everything")
    fun searchNewsForQuery(@Query("q") query: String, @Query("page") page: Int): Call<NewsSearchResponse>


    companion object {
        private const val BASE_URL = "https://newsapi.org/"

        fun create(): NewsService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .addInterceptor { chain ->
                        val originalReq = chain.request()
                        val originalHttpUrl = originalReq.url()

                        val newUrl = originalHttpUrl.newBuilder()
                                .addQueryParameter("apiKey", BuildConfig.API_KEY).build()

                        val reqst = originalReq.newBuilder().url(newUrl).build()

                        chain.proceed(reqst)
                    }
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NewsService::class.java)
        }
    }
}
