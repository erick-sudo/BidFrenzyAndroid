package com.draw.bidfrenzy.api

import com.draw.bidfrenzy.models.ProductCategory
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesResponse(
    @Json(name = "categories") val categories: List<ProductCategory>
)
