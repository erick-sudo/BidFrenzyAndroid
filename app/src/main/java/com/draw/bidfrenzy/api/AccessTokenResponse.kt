package com.draw.bidfrenzy.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessTokenResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "expires") val expires: Long,
    @Json(name = "realm") val realm: String
)
