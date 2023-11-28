package com.draw.bidfrenzy.api

import com.draw.bidfrenzy.models.ShippingAddress
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShippingAddressesResponse(
    @Json(name = "shipping_addresses") val shippingAddresses: List<ShippingAddress>
)