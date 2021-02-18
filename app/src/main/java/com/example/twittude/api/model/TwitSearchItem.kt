package com.example.twittude.api.model

import com.example.twittude.api.model.TwitMetricsItem
import com.squareup.moshi.Json

data class TwitSearchItem(
    @Json(name = "id")
    val id: Long,
    @Json(name = "text")
    val text: String? = null,
    @Json(name = "public_metrics")
    val publicMetrics: TwitMetricsItem? = null,
    @Json(name = "author_id")
    val authorId: String? = null
)