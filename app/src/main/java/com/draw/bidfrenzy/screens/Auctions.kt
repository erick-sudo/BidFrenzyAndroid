package com.draw.bidfrenzy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.draw.bidfrenzy.R
import com.draw.bidfrenzy.models.AuctionListing
import com.draw.bidfrenzy.extensions.splitUnits
import com.draw.bidfrenzy.viewmodels.AuctionListingsViewModel
import kotlinx.coroutines.delay
import java.util.Date

@Composable
fun Auctions(
    navHostController: NavHostController,
    auctionListingsViewModel: AuctionListingsViewModel = viewModel()
) {

    val auctionListings by auctionListingsViewModel.auctionsFlow.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
    ) {

        itemsIndexed(auctionListings) { _, auctionListing ->
            AuctionAdvertiserItem(
                navHostController = navHostController,
                auctionListing = auctionListing
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuctionAdvertiserItem(
    navHostController: NavHostController,
    auctionListing: AuctionListing
) {

    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            .clickable {
                UseNavigate.navigate(
                    navHostController = navHostController,
                    destination = NavRoutes.AuctionListing.route + "/${auctionListing.id}"
                )
            },
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        DateTimeCounter(
            auctionListing.offsetDate
        )
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(10.dp)
            ) {
                Text(
                    auctionListing.productName,
                )
                FloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 10.dp)
                        .size(30.dp),
                    shape = RoundedCornerShape(10),
                    onClick = {
                        UseNavigate.navigate(
                            navHostController = navHostController,
                            destination = NavRoutes.AuctionListing.route + "/${auctionListing.id}"
                        )
                    }
                ) {
                    Text(text = "Bid")
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(String.format("$ %.2f", auctionListing.currentBid))
                }
                Spacer(modifier = Modifier.height(5.dp))
                Badge() {
                    Text(String.format("$ %.2f", auctionListing.startingBid))
                }
            }
        }
    }
}

@Composable
fun DateTimeCounter(
    offsetDate: Date
) {

    var timeUnits by remember {
        mutableStateOf(Date().splitUnits(offsetDate))
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1)
            timeUnits = Date().splitUnits(offsetDate)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(start = 10.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            String.format("%02d", timeUnits[0]),
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold
            ),
            textAlign = TextAlign.Right
        )
        Text(
            "Days",
            modifier = Modifier
                .weight(1f),
            style = TextStyle(
                fontWeight = FontWeight.Light
            )
        )
        Text(
            String.format("%02d", timeUnits[1]),
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold
            ),
            textAlign = TextAlign.Right
        )
        Text(
            "Hrs",
            modifier = Modifier
                .weight(1f),
            style = TextStyle(
                fontWeight = FontWeight.Light
            )
        )
        Text(
            String.format("%02d", timeUnits[2]),
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold
            ),
            textAlign = TextAlign.Right
        )
        Text(
            "Mins",
            modifier = Modifier
                .weight(1f),
            style = TextStyle(
                fontWeight = FontWeight.Light
            )
        )
        Text(
            String.format("%02d", timeUnits[3]),
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold
            ),
            textAlign = TextAlign.Right
        )
        Text(
            "Secs",
            modifier = Modifier
                .weight(1f),
            style = TextStyle(
                fontWeight = FontWeight.Light
            )
        )
        Text(
            String.format("%02d", timeUnits[4]),
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold
            ),
            textAlign = TextAlign.Right
        )
        Text(
            "Mills",
            modifier = Modifier
                .weight(1f),
            style = TextStyle(
                fontWeight = FontWeight.Light
            )
        )
    }
}
