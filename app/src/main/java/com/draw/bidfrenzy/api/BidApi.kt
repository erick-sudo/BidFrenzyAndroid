package com.draw.bidfrenzy.api

import com.draw.bidfrenzy.models.AuctionListing
import com.draw.bidfrenzy.models.Bid
import com.draw.bidfrenzy.models.Product
import com.draw.bidfrenzy.models.ShippingAddress
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BidApi {

    @GET("products/{productId}")
    suspend fun fetchProduct(@Path("productId") productId: String): Product

    @GET("products/pagination/{pageNumber}/{itemsPerPage}")
    suspend fun fetchPage(
        @Path("pageNumber") pageNumber: Int,
        @Path("itemsPerPage") itemsPerPage: Int
    ) : ProductsResponse

    @GET("categories")
    suspend fun fetchProductCategories(): CategoriesResponse

    @GET("products/category/{categoryName}/{count}")
    suspend fun fetchProductsByCategory(@Path("categoryName") category: String, @Path("count") count: Int): ProductsResponse

    @GET("users/cart")
    suspend fun fetchUsersCart(): CartResponse

    @GET("shipping/addresses")
    suspend fun fetchShippingAddresses(@Query("q") search: String): ShippingAddressesResponse

    @GET("shipping/addresses/{addressId}")
    suspend fun fetchShippingAddress(@Path("addressId") addressId: String): ShippingAddress

    @GET("auction/listings")
    suspend fun fetchAuctionListings(): List<AuctionListing>

    @POST("login")
    suspend fun login(@Body loginCredentials: Map<String, String>): AccessTokenResponse

    @POST("register")
    suspend fun register(@Body registrationInfo: Map<String, String>): ApiResponse

    @GET("auction/listings/{auctionListingId}")
    suspend fun fetchAuctionListing(@Path("auctionListingId") auctionListingId: String): AuctionListing

    @GET("auction/listings/bids/{auctionListingId}")
    suspend fun fetchAuctionListingsBids(@Path("auctionListingId") auctionListingId: String): List<Bid>
}