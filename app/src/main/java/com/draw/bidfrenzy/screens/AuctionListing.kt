package com.draw.bidfrenzy.screens

import android.text.format.DateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.draw.bidfrenzy.R
import com.draw.bidfrenzy.extensions.toCurrency
import com.draw.bidfrenzy.ui.Zoom
import com.draw.bidfrenzy.viewmodels.AuctionListingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuctionListing(
    auctionListingId: String,
    auctionListingViewModel: AuctionListingViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    val auctionListingUiState by auctionListingViewModel.auctionListingFlow.collectAsState()

    LaunchedEffect(auctionListingId) {
        auctionListingViewModel.initializeAuctionListingFlow(auctionListingId)
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {


        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(5)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 30.dp)
            ) {
                DateTimeCounter(offsetDate = auctionListingUiState.auctionListing.offsetDate)
            }
        }

        Row(

        ) {
            Box(
                modifier = Modifier
                    .background(Color.Blue)
                    .weight(1f)
            ) {
                Zoom() {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Laptop",
                        modifier = Modifier
                            .padding(15.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }

            Column(
                modifier = Modifier
                    .height(200.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = auctionListingUiState.auctionListing.productName)
                Spacer(modifier = Modifier.height(5.dp))
                Badge(
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        text = auctionListingUiState.auctionListing.currentBid.toCurrency()
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Badge() {
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        text = auctionListingUiState.auctionListing.startingBid.toCurrency()
                    )
                }
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
            ) {
                auctionListingUiState.bids.forEach { bid ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp),
                        shape = RoundedCornerShape(3)
                    ) {

                        Brush.horizontalGradient()
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_access_time_filled_24), contentDescription = "Time")
                                
                                Text(
                                    modifier = Modifier
                                        .weight(1f),
                                    text = DateFormat.format("EEEE, MMMM dd, HH:mm:ss", bid.placedAt).toString(),
                                    style = TextStyle(
                                        textAlign = TextAlign.Right
                                    )
                                )
                            }

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = bid.bidder
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(start = 20.dp)
                            ) {
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_diamond_24), contentDescription = "Bid Amount")
                                Text(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 10.dp),
                                    text = bid.bidAmount.toCurrency(),
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}