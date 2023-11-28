package com.draw.bidfrenzy.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.draw.bidfrenzy.models.AuctionListing
import com.draw.bidfrenzy.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class AuctionListingsViewModel : ViewModel() {

    private val productRepository = ProductRepository()

    private var _auctionsFlow = MutableStateFlow<List<AuctionListing>>(emptyList())
    val auctionsFlow
        get() = _auctionsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _auctionsFlow.value = productRepository.fetchAuctionListings()
            } catch(e: Exception) {
                Log.d("ProductCategoryViewModel", "${e.message}")
            }
        }
    }
}