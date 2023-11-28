package com.draw.bidfrenzy.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.draw.bidfrenzy.ui.ProductCategories

@Composable
fun Home(
    navHostController: NavHostController
) {
    ProductCategories(
        navHostController = navHostController
    )
}