package com.draw.bidfrenzy.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Bid(
    @Json(name = "id") val id: String,
    @Json(name = "bidder") val bidder: String,
    @Json(name = "bidder_id") val bidderId: String,
    @Json(name = "auction_id") val auctionId: String,
    @Json(name = "bid_amount") val bidAmount: Double,
    @Json(name = "placed_at") val placedAt: Long
)
