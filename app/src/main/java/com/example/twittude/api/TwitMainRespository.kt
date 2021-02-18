package com.example.twittude.api

import com.example.twittude.model.TwitListItem
import kotlinx.coroutines.flow.Flow

interface TwitMainRepository {


    suspend fun getTwitterAuthentication(bearer: String)

    suspend fun checkCacheForBearer()

    suspend fun searchQuery(queryString: String): List<TwitListItem>

    suspend fun saveTweetToDisk(tweet: String)

    suspend fun readTweetFromDisk(): Flow<String>

    suspend fun saveBearerTokenToDisk(token: String)

    suspend fun readBearerTokenFromDisk(): Flow<String>
}