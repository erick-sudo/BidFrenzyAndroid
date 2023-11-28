package com.draw.bidfrenzy.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.draw.bidfrenzy.ui.CartListItem
import com.draw.bidfrenzy.viewmodels.CartViewModel

@Composable
fun Cart(
    navHostController: NavHostController,
    cartViewModel: CartViewModel
) {

    val cartUiState by cartViewModel.cartStateFlow.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .verticalScroll(scrollState)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
        ) {
            Column {
                cartUiState.cartItems.forEach { cartItem ->
                    CartListItem(
                        cartItem = cartItem,
                        changeQuantity = { offset ->
                            cartViewModel.updateCart(cartItem.productId, offset)
                        }
                    )

                }
            }
        }

        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Total price"
                    )
                    Spacer(
                        modifier = Modifier
                    )
                    Text(
                        text = String.format("%.2f", cartUiState.totalPrice),
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold, fontSize = 20.sp
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "VAT"
                    )
                    Spacer(
                        modifier = Modifier
                    )
                    Text(
                        text = String.format("%.2f", cartUiState.vat),
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            onClick = {
                UseNavigate.navigate(
                    navHostController = navHostController,
                    NavRoutes.Checkout
                )
            }
        ) {
            Text(text = "Checkout")
        }
    }

}