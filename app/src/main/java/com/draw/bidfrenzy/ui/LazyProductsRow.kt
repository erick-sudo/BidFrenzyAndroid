package com.draw.bidfrenzy.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.draw.bidfrenzy.models.ProductItem
import kotlinx.coroutines.launch

@Composable
fun LazyProductsRow(
    productItems: List<ProductItem>,
    navHostController: NavHostController,
    requestNextPage: ((Int) -> Unit) -> Unit,
) {

    val lazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        LazyRow(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        ) {
            itemsIndexed(productItems) {_, productItem ->
                Box(
                    modifier = Modifier
                        .width(100.dp)
                ) {
                    ProductListItem(
                        product = productItem,
                        navHostController = navHostController
                    )
                }

            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(30.dp),
            onClick = {
                val firstVisible = lazyListState.firstVisibleItemIndex
                println("FirstVisible $firstVisible")
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(firstVisible)
                }
            }
        ) {
            Icon(Icons.Filled.ArrowBack, "Scroll Left")
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(30.dp),
            onClick = {
                requestNextPage() { lastItem ->
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(lastItem)
                    }
                }
            }
        ) {
            Icon(Icons.Filled.ArrowForward, "Scroll Right")
        }
    }
}