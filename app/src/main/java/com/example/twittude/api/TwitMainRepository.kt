package com.example.twittude.api

import io.reactivex.annotations.CheckReturnValue
import twitter4j.QueryResult
import twitter4j.Twitter

interface TwitMainRepository {

    @CheckReturnValue
    suspend fun getTwitterAuthentication(): Twitter

    @CheckReturnValue
    suspend fun searchQuery(queryString: String): QueryResult
}