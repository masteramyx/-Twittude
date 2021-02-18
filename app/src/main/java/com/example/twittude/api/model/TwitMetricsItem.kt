package com.example.twittude.api.model

import com.squareup.moshi.Json

data class TwitMetricsItem(
    @Json(name = "retweet_count")
    val retweetCount: Int,
    @Json(name = "reply_count")
    val replyCount: Int,
    @Json(name = "like_count")
    val likeCount: Int,
    @Json(name = "quote_count")
    val quoteCount: Int
)