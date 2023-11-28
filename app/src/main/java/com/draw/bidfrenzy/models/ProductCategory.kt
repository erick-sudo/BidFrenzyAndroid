package com.draw.bidfrenzy.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductCategory(
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String
)