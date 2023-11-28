package com.draw.bidfrenzy.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.draw.bidfrenzy.R
import com.draw.bidfrenzy.viewmodels.ProductCategoryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductCategories(
    navHostController: NavHostController,
    productCategoryViewModel: ProductCategoryViewModel = viewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()

    val groupedProductsStateFlow by productCategoryViewModel.groupedProductsFlow.collectAsState()

    val displayScrollToTopButton by remember{
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 5
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .padding(5.dp)
        ) {

            groupedProductsStateFlow.forEach { (category, productItems) ->
                stickyHeader {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = category,
                            modifier = Modifier
                                .padding(5.dp)
                        )
                    }
                }

                item {
                    LazyProductsRow(
                        productItems = productItems,
                        navHostController = navHostController,
                        requestNextPage = { callback ->
                            coroutineScope.launch {
                                productCategoryViewModel.nextPage(category, callback)
                            }
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(40.dp)
                .padding(bottom = 10.dp),
            visible = displayScrollToTopButton
        ) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.twotone_keyboard_double_arrow_up_24),
                    contentDescription = "Scroll To Top"
                )
            }
        }
    }
}