package com.draw.bidfrenzy.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.draw.bidfrenzy.models.Product
import com.draw.bidfrenzy.models.ProductItem
import com.draw.bidfrenzy.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel: ViewModel() {

    private val productRepository: ProductRepository = ProductRepository()

    private val _productDetailsStateFlow = MutableStateFlow(Product())
    val productDetailsStateFlow = _productDetailsStateFlow.asStateFlow()

    private val _relatedProductsStateFlow = MutableStateFlow<List<ProductItem>>(emptyList())
    val relatedProductsStateFlow
        get() = _relatedProductsStateFlow.asStateFlow()

    fun fetchProduct(productId: String) {
        viewModelScope.launch {
            try {
                _productDetailsStateFlow.value = productRepository.fetchProduct(productId)

                nextPage(
                    productCategory = productDetailsStateFlow.value.category,
                    count = 7
                )
            } catch (e: Exception) {
                Log.d("ProductDetailsStateFlow", "${e.message}")
            }
        }
    }

    suspend fun nextPage(productCategory: String, count: Int = 1, callback: (Int) -> Unit = {  }) {
        val newPage = productRepository.fetchProductsByCategory(category = productCategory, count = count)
        var newLength = newPage.size
        _relatedProductsStateFlow.update { relatedProducts ->
            var copy = relatedProducts
            val prods = relatedProducts + newPage
            copy = prods
            newLength = prods.size
            copy
        }

        callback(newLength - 1)
    }

}