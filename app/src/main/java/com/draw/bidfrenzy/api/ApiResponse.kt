package com.draw.bidfrenzy.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "status") val status: Int,
    @Json(name = "message") val message: String
)
