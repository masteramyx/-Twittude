package com.example.twittude.api

import io.reactivex.annotations.CheckReturnValue
import kotlinx.coroutines.flow.Flow
import twitter4j.QueryResult
import twitter4j.Twitter

interface TwitMainRepository {

    @CheckReturnValue
    suspend fun getTwitterAuthentication(): Twitter

    @CheckReturnValue
    suspend fun searchQuery(queryString: String): QueryResult

    suspend fun incrementCounter()

    suspend fun saveTweetToDisk(tweet: String)

    suspend fun readTweetFromDisk(): Flow<String>
}