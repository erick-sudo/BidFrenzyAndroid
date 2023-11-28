package com.draw.bidfrenzy.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.draw.bidfrenzy.models.ProductCategory
import com.draw.bidfrenzy.models.ProductItem
import com.draw.bidfrenzy.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductCategoryViewModel: ViewModel() {

    private val productRepository = ProductRepository()

    private var _categoryFlow = MutableStateFlow<List<ProductCategory>>(emptyList())
    private val categoryFlow
        get() = _categoryFlow.asStateFlow()

    private var _groupedProductsFlow = MutableStateFlow<Map<String, List<ProductItem>>>(emptyMap())
    val groupedProductsFlow
        get() = _groupedProductsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _categoryFlow.value = productRepository.fetchProductCategories()

                initializeCategories(categoryFlow.value)
            } catch(e: Exception) {
                Log.d("ProductCategoryViewModel", "${e.message}")
            }
        }
    }

    private suspend fun initializeCategories(productCategories: List<ProductCategory>) {
        productCategories.forEach { category ->
            viewModelScope.launch {
                _groupedProductsFlow.update {
                    var copy = it
                    copy = copy + (category.name to productRepository.fetchProductsByCategory(category = category.name, count = 7))
                    copy
                }
            }
        }
    }

    suspend fun nextPage(productCategory: String, callback: (Int) -> Unit) {
        val newPage = productRepository.fetchProductsByCategory(category = productCategory, count = 1)
        var newLength = newPage.size
        _groupedProductsFlow.update { groupedProducts ->
            var copy = groupedProducts
            val prods = (copy[productCategory] ?: emptyList()) + newPage
            copy = copy + (productCategory to prods)
            newLength = prods.size
            copy
        }

        callback(newLength - 1)
    }
}