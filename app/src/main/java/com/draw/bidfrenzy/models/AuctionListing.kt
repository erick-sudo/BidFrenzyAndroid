package com.draw.bidfrenzy.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class AuctionListing(
    @Json(name = "id") val id: String = "",
    @Json(name = "product_id") val productId: String = "",
    @Json(name = "product_name") val productName: String = "",
    @Json(name = "thumbnail") val thumbnail: String = "",
    @Json(name = "thresh_hold") val threshHold: Double = 0.0,
    @Json(name = "starting_bid") val startingBid: Double = 0.0,
    @Json(name = "current_bid") val currentBid: Double = 0.0,
    @Json(name = "created_at") val createdAt: Long = Date().time,
) {

    val offsetDate
        get() = Date(createdAt)
}