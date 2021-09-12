package com.dm3010.keddit.features.news

import com.dm3010.keddit.api.NewsAPI
import com.dm3010.keddit.api.NewsRestAPI
import com.dm3010.keddit.commons.RedditNews
import com.dm3010.keddit.commons.RedditNewsItem
import rx.Observable

class NewsManager(private val api: NewsAPI = NewsRestAPI()) {

    fun getNews(after: String, limit: String = "10"): Observable<RedditNews> {
        return Observable.create { subscriber ->
            val callResponse = api.getNews(after, limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val dataResponse = response.body()?.data
                val news = dataResponse?.children?.map {
                    with(it.data, {
                        RedditNewsItem(author, title, num_comments, created, thumbnail, url.orEmpty())
                    })
                }
                val redditNews = RedditNews(
                    dataResponse?.before.orEmpty(),
                    dataResponse?.after.orEmpty(),
                    news ?: ArrayList()
                )
                subscriber.onNext(redditNews)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}