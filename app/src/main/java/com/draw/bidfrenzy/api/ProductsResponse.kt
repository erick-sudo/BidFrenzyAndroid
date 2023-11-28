package com.draw.bidfrenzy.api

import com.draw.bidfrenzy.models.ProductItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductsResponse(
    @Json(name = "products") val products: List<ProductItem>
)
