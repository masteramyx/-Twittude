package com.example.twittude.api.response

import com.squareup.moshi.Json


data class AuthResponse(
    @Json(name = "token_type")
    val token_type: String,
    @Json(name = "access_token")
    val access_token: String
)