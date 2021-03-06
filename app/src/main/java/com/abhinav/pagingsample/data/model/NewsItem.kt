package com.abhinav.pagingsample.data.model

import com.google.gson.annotations.SerializedName

data class NewsItem(
        @SerializedName("author") var author: String?, //http://www.abc.net.au/news/5511636, http://www.abc.net.au/news/lucy-sweeney/7114718
        @SerializedName("title") var title: String?, //Senate President reignites citizenship debacle, says father may be British
        @SerializedName("description") var description: String?, //Liberal senator Stephen Parry tells the Government he may be a dual citizen, saying he has asked British authorities to examine his history.
        @SerializedName("url") var url: String?, //http://www.abc.net.au/news/2017-10-31/stephen-parry-tells-government-his-father-may-be-british/9104482
        @SerializedName("urlToImage") var urlToImage: String?, //http://www.abc.net.au/news/image/9105122-1x1-700x700.jpg
        @SerializedName("publishedAt") var publishedAt: String? //2017-10-31T07:02:48Z
)
