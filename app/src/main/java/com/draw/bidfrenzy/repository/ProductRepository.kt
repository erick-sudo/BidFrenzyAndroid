package com.draw.bidfrenzy.repository

import com.draw.bidfrenzy.api.BidApi
import com.draw.bidfrenzy.api.AccessTokenResponse
import com.draw.bidfrenzy.api.ApiResponse
import com.draw.bidfrenzy.models.AuctionListing
import com.draw.bidfrenzy.models.CartItem
import com.draw.bidfrenzy.models.Product
import com.draw.bidfrenzy.models.ProductCategory
import com.draw.bidfrenzy.models.ProductItem
import com.draw.bidfrenzy.models.ShippingAddress
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class ProductRepository {

    suspend fun fetchProductCategories(): List<ProductCategory> = API_INSTANCE.fetchProductCategories().categories

    suspend fun fetchProductsByCategory(category: String, count: Int): List<ProductItem> = API_INSTANCE.fetchProductsByCategory(category, count).products

    suspend fun fetchProduct(productId: String): Product =  API_INSTANCE.fetchProduct(productId)

    suspend fun fetchPage(
        pageNumber: Int,
        itemsPerPage: Int
    ) : List<ProductItem> = API_INSTANCE.fetchPage(pageNumber, itemsPerPage).products

    suspend fun fetchUsersCart() : List<CartItem> = API_INSTANCE.fetchUsersCart().cartItems

    suspend fun fetchShippingAddresses(search: String) : List<ShippingAddress> = API_INSTANCE.fetchShippingAddresses(search).shippingAddresses

    suspend fun fetchShippingAddress(addressId: String): ShippingAddress = API_INSTANCE.fetchShippingAddress(addressId)

    suspend fun fetchAuctionListings(): List<AuctionListing> = API_INSTANCE.fetchAuctionListings()

    suspend fun login(loginCredentials: Map<String, String>): AccessTokenResponse = API_INSTANCE.login(loginCredentials)

    suspend fun register(registrationInfo: Map<String, String>): ApiResponse = API_INSTANCE.register(registrationInfo)

    suspend fun fetchAuctionListing(auctionListingId: String): AuctionListing = API_INSTANCE.fetchAuctionListing(auctionListingId)

    suspend fun fetchAuctionListingsBids(auctionListingId: String) = API_INSTANCE.fetchAuctionListingsBids(auctionListingId)

    companion object {

        private val API_INSTANCE: BidApi by lazy {
            // Http Client
            val okHttpClient: OkHttpClient = OkHttpClient
                .Builder()
                .build()

            // Retrofit builder
            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("http://10.0.2.2:3001/api/v1/")
                .client(okHttpClient)
                .build()

            // Initialize api
            retrofit.create()
        }
    }
}