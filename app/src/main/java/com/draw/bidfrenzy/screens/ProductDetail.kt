package com.draw.bidfrenzy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.draw.bidfrenzy.R
import com.draw.bidfrenzy.models.CartItem
import com.draw.bidfrenzy.models.ProductItem
import com.draw.bidfrenzy.ui.Heading
import com.draw.bidfrenzy.ui.ImageViewDialog
import com.draw.bidfrenzy.ui.LazyProductsRow
import com.draw.bidfrenzy.ui.Table
import com.draw.bidfrenzy.viewmodels.CartViewModel
import com.draw.bidfrenzy.viewmodels.ProductDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductDetail(
    productId: String,
    productDetailViewModel: ProductDetailViewModel = viewModel(),
    cartViewModel: CartViewModel,
    navHostController: NavHostController
) {

    val coroutineScope = rememberCoroutineScope()

    var selectedImage by remember {
        mutableStateOf("")
    }

    var zoomingImage by remember {
        mutableStateOf(false)
    }

    val scrollState = rememberScrollState()

    val productUiState by productDetailViewModel.productDetailsStateFlow.collectAsState()

    val relatedProductItems by productDetailViewModel.relatedProductsStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        productDetailViewModel.fetchProduct(productId)
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        if(zoomingImage) {
            ImageViewDialog(
                imageUrl = selectedImage
            ) {
                zoomingImage = false
            }
        }

        LazyRow(

        ) {

            itemsIndexed(productUiState.thumbnails) { _, thumbnailUrl ->
                Card(
                    modifier = Modifier
                        .height(250.dp)
                        .width(200.dp)
                        .padding(5.dp)
                        .clickable {
                            selectedImage = thumbnailUrl
                            zoomingImage = true
                        },
                    shape = RoundedCornerShape(2.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Laptop",
                        modifier = Modifier
                            .padding(15.dp)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }

        Text(
            text = "${productUiState.name} i5 8th Gen. 4GB RAM/256SSD/2GHz",
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "$ ${String.format("%.2f", productUiState.price)}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp),
                shape = RoundedCornerShape(20),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_monetization_on_24),
                    contentDescription = "Buy"
                )
            }
            Button(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp),
                shape = RoundedCornerShape(20),
                onClick = {
                    coroutineScope.launch {
                        cartViewModel.updateCart(
                            CartItem(productUiState)
                        )
                        UseNavigate.navigate(navHostController, NavRoutes.Cart)
                    }
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_add_shopping_cart_24),
                    contentDescription = "Add to cart"
                )
            }
        }

        Heading(
            text = "Description"
        )

        Text(
            text = productUiState.description,
            modifier = Modifier.padding(10.dp)
        )

        Heading(
            text = "Properties"
        )

        Table(
            tableData = productUiState.attributes,
            rowSeparator = {
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .fillMaxWidth(0.9f)
                        .border(width = 2.dp, color = MaterialTheme.colorScheme.surfaceTint)
                )
            }
        )

        RelatedProducts(
            productItems = relatedProductItems,
            category = productUiState.category,
            navHostController = navHostController,
            productDetailViewModel = productDetailViewModel
        )
    }
}

@Composable
fun RelatedProducts(
    productDetailViewModel: ProductDetailViewModel,
    category: String,
    productItems: List<ProductItem>,
    navHostController: NavHostController
) {

    val coroutineScope = rememberCoroutineScope()

    Column {
        Heading(
            text = "Related"
        )

        LazyProductsRow(
            productItems = productItems,
            navHostController = navHostController,
            requestNextPage = { callback ->
                coroutineScope.launch {
                    productDetailViewModel.nextPage(
                        productCategory = category,
                        callback = callback
                    )
                }
            }
        )
    }
}