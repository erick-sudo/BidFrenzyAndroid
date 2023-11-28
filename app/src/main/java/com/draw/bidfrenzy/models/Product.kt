package com.draw.bidfrenzy.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id") val id: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "description") val description: String = "",
    @Json(name = "thumbnails") val thumbnails: List<String> = emptyList(),
    @Json(name = "price") val price: Double = 0f.toDouble(),
    @Json(name = "category") val category: String = "",
    @Json(name = "attributes") val attributes: Map<String, String> = emptyMap()
)

@JsonClass(generateAdapter = true)
data class ProductItem(
    @Json(name = "id") val id: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "description") val description: String = "",
    @Json(name = "thumbnail") val thumbnail: String = "",
    @Json(name = "price") val price: Double = 0f.toDouble(),
    @Json(name = "category") val category: String = ""
)

@JsonClass(generateAdapter = true)
data class CartItem(
    @Json(name = "product_id") val productId: String,
    @Json(name = "product_name") val productName: String,
    @Json(name = "thumbnail") val thumbnail: String,
    @Json(name = "cart_id") val cartId: String,
    @Json(name = "quantity")  var quantity: Int,
    @Json(name = "price") val price: Double,
    @Json(name = "status") val status: Boolean
) {
    constructor(product: Product) : this(
        productId = product.id,
        productName = product.name,
        cartId = "CARTID",
        quantity = 1,
        status = false,
        price = product.price,
        thumbnail = product.thumbnails[0]
    )
}