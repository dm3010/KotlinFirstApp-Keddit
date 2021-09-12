package com.dm3010.keddit.api

import retrofit2.Call

interface NewsAPI {
    fun getNews(after: String, limit: String) : Call<RedditNewsResponse>
}