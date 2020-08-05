package com.example.twittude.api

import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import twitter4j.QueryResult
import twitter4j.Twitter

interface TwitMainRepository {

    @CheckReturnValue
    fun getTwitterAuthentication(): Single<Twitter>

    @CheckReturnValue
    fun searchQuery(query: String): Single<QueryResult>
}