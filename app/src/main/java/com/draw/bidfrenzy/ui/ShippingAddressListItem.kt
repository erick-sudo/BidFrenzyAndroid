package com.draw.bidfrenzy.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.draw.bidfrenzy.R
import com.draw.bidfrenzy.models.ShippingAddress


@Composable
fun ShippingAddressListItem(
    modifier: Modifier = Modifier,
    shippingAddress: ShippingAddress,
    onClick: (ShippingAddress) -> Unit = {  }
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 5.dp, end = 5.dp)
            .clickable {
                onClick(shippingAddress)
            }
            .then(modifier),
        shape = RoundedCornerShape(1.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = shippingAddress.name
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = shippingAddress.country,
                    fontSize = 15.sp
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp)
                )
                Text(
                    text = shippingAddress.county,
                    fontSize = 15.sp
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp)
                )
                Text(
                    text = shippingAddress.subCounty,
                    fontSize = 15.sp
                )
            }
            Row() {
                Box(
                    modifier = Modifier.width(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_location_searching_24),
                        contentDescription = "Location"
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = shippingAddress.streetAddress,
                        fontSize = 10.sp
                    )

                    Text(
                        text = shippingAddress.streetAddress2,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}