package com.draw.bidfrenzy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.draw.bidfrenzy.R
import com.draw.bidfrenzy.models.ProductItem
import com.draw.bidfrenzy.screens.NavRoutes
import com.draw.bidfrenzy.screens.UseNavigate

@Composable
fun ProductListItem(
    product: ProductItem,
    navHostController: NavHostController
) {

    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .width(width = 100.dp)
            .fillMaxWidth()
            .padding(top = 5.dp, start = 5.dp)
            .clickable {
                UseNavigate.navigate(
                    navHostController,
                    "${NavRoutes.ProductDetails.route}/${product.id}"
                )
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 100.dp)
                .padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = product.description,

            )
        }

        Column(
            modifier = Modifier
                .height(40.dp)
        ) {
            Text(
                text = product.name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "$ ${String.format("%.2f", product.price)}",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.padding(start = 6.dp, bottom = 4.dp)
            )
        }

    }
}