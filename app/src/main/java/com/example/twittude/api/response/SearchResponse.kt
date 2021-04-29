package com.example.twittude.api.response

import com.example.twittude.api.model.TwitSearchItem
import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "data")
    val tweets: List<TwitSearchItem>
)