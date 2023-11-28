package com.draw.bidfrenzy.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShippingAddress(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "country") val country: String,
    @Json(name = "county") val county: String,
    @Json(name = "sub_county") val subCounty: String,
    @Json(name = "street_address") val streetAddress: String,
    @Json(name = "street_address2") val streetAddress2: String
) {

    constructor() : this(
        "", "", "", "", "", "", ""
    )
}