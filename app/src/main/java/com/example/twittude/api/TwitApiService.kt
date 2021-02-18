package com.example.twittude.api

import android.util.Base64
import com.example.twittude.BuildConfig
import com.example.twittude.api.response.AuthResponse
import com.example.twittude.api.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import java.net.URLEncoder

interface TwitApiService {


    @Headers(
        "Content-Type: application/x-www-form-urlencoded;charset=UTF-8",
        "Cache-Control: public, max-age=604800"
    )
    @POST("oauth2/token")
    suspend fun authenticate(
        @Query("grant_type")
        type: String = "client_credentials"
    ): AuthResponse


    @GET("2/tweets/search/recent")
    suspend fun search(
        @Query("query")
        query: String,
        @Query("max_results")
        maxResults: Int = 100,
        @Query("expansions")
        expansions: String = "author_id"
    ): SearchResponse

    companion object {
        val urlEncodedKey = URLEncoder.encode(BuildConfig.TWIT_CONSUMER_KEY, "UTF-8")
        val urlEncodedSecret = URLEncoder.encode(BuildConfig.TWIT_CONSUMER_SECRET, "UTF-8")
        val combo = "$urlEncodedKey:$urlEncodedSecret"
        val base64Encoded = Base64.encode(combo.toByteArray(), Base64.DEFAULT)
    }
}