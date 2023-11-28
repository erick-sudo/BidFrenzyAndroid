package com.draw.bidfrenzy.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.draw.bidfrenzy.viewmodels.CartViewModel

@Composable
fun FrenzyNavHost(
    cartViewModel: CartViewModel,
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = NavRoutes.Home.route
    ) {

        composable(NavRoutes.Home.route) {
            Home(
                navHostController = navHostController
            )
        }

        composable(NavRoutes.Cart.route) {
            Cart(
                cartViewModel = cartViewModel,
                navHostController = navHostController
            )
        }

        composable(NavRoutes.Checkout.route) {
            Checkout(
                cartViewModel = cartViewModel
            )
        }

        composable(NavRoutes.Account.route) {
            Account(
                // navHostController = navHostController
            )
        }

        composable(NavRoutes.Login.route) {
            Login(
                navHostController = navHostController
            )
        }

        composable(NavRoutes.Register.route) {
            Register(
                navHostController = navHostController
            )
        }

        composable( "productDetails/{productId}") { backStackEntry ->

            val productId = backStackEntry.arguments?.getString("productId")

            ProductDetail(
                productId = productId ?: "",
                navHostController = navHostController,
                cartViewModel = cartViewModel
            )
        }

        composable("auctionListing/{auctionListingId}") { backStackEntry ->
            val auctionListingId = backStackEntry.arguments?.getString("auctionListingId")

            AuctionListing(auctionListingId = auctionListingId ?: "")
        }

        composable(NavRoutes.Auctions.route) {
            Auctions(
                navHostController = navHostController
            )
        }

        composable(NavRoutes.Spin.route) {
            Spin()
        }
    }
}