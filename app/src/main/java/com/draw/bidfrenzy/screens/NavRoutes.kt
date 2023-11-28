package com.draw.bidfrenzy.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

sealed class NavRoutes(
    val route: String
) {
    object Home: NavRoutes("home")
    object Cart: NavRoutes("cart")
    object Checkout: NavRoutes("checkout")
    object Account: NavRoutes("account")
    object Login: NavRoutes("login")
    object Register: NavRoutes("register")
    object ProductDetails: NavRoutes("productDetails")
    object Auctions: NavRoutes("auctions")
    object Spin: NavRoutes("spin")
    object AuctionListing: NavRoutes("auctionListing")
}

object UseNavigate {
    fun navigate(navHostController: NavHostController, destination: NavRoutes) {
        navHostController.navigate(destination.route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigate(navHostController: NavHostController, destination: String) {
        navHostController.navigate(destination) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}