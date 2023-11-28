package com.draw.bidfrenzy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.draw.bidfrenzy.R
import com.draw.bidfrenzy.models.CartItem

@Composable
fun CartListItem(
    cartItem: CartItem,
    changeQuantity: (Int) -> Unit = { }
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 5.dp, end = 5.dp),
        shape = RoundedCornerShape(1.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(60.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = cartItem.productId
                )
            }
            Column() {
                Text(
                    text = cartItem.productName,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .weight(weight = 1f, fill = true)
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FloatingActionButton(
                            modifier = Modifier
                                .size(20.dp),
                            onClick = {
                                if(cartItem.quantity > 1) {
                                    changeQuantity(-1)
                                }
                            }
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.baseline_remove_24),
                                null
                            )
                        }
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.ExtraLight
                                    )
                                ) {
                                    append("qty ")
                                }

                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                ) {
                                    append(cartItem.quantity.toString())
                                }
                            }
                        )
                        FloatingActionButton(
                            modifier = Modifier
                                .size(20.dp),
                            onClick = {
                                changeQuantity(1)
                            }
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.baseline_add_24),
                                null
                            )
                        }
                    }

                    Text(
                        text = "$ ${String.format("%.2f", cartItem.price)}",
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(end = 10.dp, bottom = 10.dp)
                        .fillMaxWidth(),
                    text = String.format("%.2f", cartItem.price * cartItem.quantity),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Right
                    )
                )
            }
        }
    }
}