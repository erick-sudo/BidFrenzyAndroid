package com.draw.bidfrenzy.api

import com.draw.bidfrenzy.models.CartItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CartResponse(
    @Json(name = "cart_items") val cartItems: List<CartItem>
)
