package com.draw.bidfrenzy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.draw.bidfrenzy.R
import com.draw.bidfrenzy.models.ModeOfPayment
import com.draw.bidfrenzy.models.PaymentPolicy
import com.draw.bidfrenzy.models.ShippingAddress
import com.draw.bidfrenzy.ui.ShippingAddressListItem
import com.draw.bidfrenzy.viewmodels.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Checkout(
    cartViewModel: CartViewModel
) {
    val shippingAddressStateFlow by cartViewModel.shippingAddressStateFlow.collectAsState()
    val cartUiState by cartViewModel.cartStateFlow.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val rememberShippingAddress by cartViewModel.isRememberingShippingAddress.collectAsState(false)

    var search by remember {
        mutableStateOf("")
    }

    val onAddressSearchChange: (String) -> Unit = { newSearch ->
        search = newSearch
        cartViewModel.fetchShippingAddresses(search)
    }

    val onPaymentPolicyChange: (PaymentPolicy) -> Unit = { newPaymentPolicy ->
        cartViewModel.updateOrder(newPaymentPolicy)
    }

    val onShippingAddressSelection: (ShippingAddress) -> Unit = { newShippingAddress ->
        cartViewModel.updateOrder(newShippingAddress)
        cartViewModel.clearShippingAddresses()
        search = ""
    }

    val scrollState = rememberScrollState()

    Column(
        Modifier.verticalScroll(scrollState)
    )
    {
        Card(
            modifier = Modifier
                .padding(5.dp),
            shape = RoundedCornerShape(1.dp)
        ) {
            TextField(
                value = search,
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = onAddressSearchChange,
                leadingIcon = {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.baseline_local_shipping_24),
                        contentDescription = "Shipping"
                    )
                },
                trailingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search Shipping Address")
                },
                placeholder = {
                    Text("Shipping Address")
                }
            )

            Box(

            ) {

                if(cartUiState.order?.shippingAddress != null) {
                    Column(

                    ) {
                        ShippingAddressListItem(
                            shippingAddress = cartUiState.order!!.shippingAddress!!,
                            modifier = Modifier.padding(5.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Checkbox(
                                checked = rememberShippingAddress,
                                onCheckedChange = { checked ->
                                    coroutineScope.launch {
                                        cartViewModel.toggleRememberShippingAddress(checked)
                                        if(checked) {
                                            cartUiState.order?.shippingAddress?.id?.let { id ->
                                                cartViewModel.setPreferredShippingAddress(id)
                                            }
                                        } else {
                                            cartViewModel.setPreferredShippingAddress("")
                                        }
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Remember address",
                                fontSize = 15.sp
                            )
                        }
                    }
                }

                Box() {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        shippingAddressStateFlow.map { shippingAddress ->
                            ShippingAddressListItem(
                                shippingAddress = shippingAddress,
                                onClick = onShippingAddressSelection
                            )
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(1.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.baseline_summarize_24),
                    contentDescription = "Summary"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Order Summary",
                    fontSize = 20.sp
                )
            }


            Column(
                modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Total Price"
                    )
                    Spacer(
                        modifier = Modifier
                    )
                    Text(
                        text = String.format("%.2f", 14440.80)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "VAT (16%)"
                    )
                    Spacer(
                        modifier = Modifier
                    )
                    Text(
                        text = String.format("%.2f", 125.34)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Shipping Fee"
                    )
                    Spacer(
                        modifier = Modifier
                    )
                    Text(
                        text = String.format("%.2f", 223.45)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                        .border(width = 4.dp, color = MaterialTheme.colorScheme.primary)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "TOTAL",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier
                    )
                    Text(
                        text = String.format("%.2f", 168345.53),
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(1.dp),
            colors = CardDefaults.cardColors()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.baseline_local_police_24),
                    contentDescription = "Shipping"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Payment policy",
                    fontSize = 20.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                RadioButton(selected = cartUiState.order?.paymentPolicy == PaymentPolicy.PayOnDelivery, onClick = { onPaymentPolicyChange(
                    PaymentPolicy.PayOnDelivery) })
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Payment on Delivery",
                    fontSize = 15.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                RadioButton(selected = cartUiState.order?.paymentPolicy == PaymentPolicy.PayOnOrder, onClick = { onPaymentPolicyChange(
                    PaymentPolicy.PayOnOrder) })
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Payment on Order",
                    fontSize = 15.sp
                )
            }
        }

        Card(
            modifier = Modifier
                .padding(5.dp)
                .background(Color.Red)
                .fillMaxWidth(),
            shape = RoundedCornerShape(1.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.baseline_payments_24),
                    contentDescription = "Payment"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Method of payment",
                    fontSize = 20.sp
                )
            }

            ModeOfPayment.values().forEach { modeOfPayment ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .height(100.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Icon(
                            ImageVector.vectorResource(id = modeOfPayment.icon),
                            contentDescription = modeOfPayment.name
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = modeOfPayment.name,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            onClick = {

            },
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = "Place Order"
            )
        }
    }
}