package com.draw.bidfrenzy.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.draw.bidfrenzy.models.AuctionListing
import com.draw.bidfrenzy.models.Bid
import com.draw.bidfrenzy.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuctionListingViewModel: ViewModel() {

    private val productRepository = ProductRepository()

    private var _auctionListingUiStateFlow = MutableStateFlow(AuctionListingUiState())
    val auctionListingFlow
        get() = _auctionListingUiStateFlow.asStateFlow()

    fun initializeAuctionListingFlow(auctionListingId: String) {
        viewModelScope.launch {
            try {
                _auctionListingUiStateFlow.update { uiState ->
                    uiState.copy(
                        auctionListing = productRepository.fetchAuctionListing(auctionListingId)
                    )
                }

                _auctionListingUiStateFlow.update { uiState ->
                    uiState.copy(
                        bids = productRepository.fetchAuctionListingsBids(auctionListingId)
                    )
                }
            } catch(e: Exception) {
                Log.d("AuctionListingViewModel", "${e.message}")
            }
        }
    }
}

data class AuctionListingUiState(
    val auctionListing: AuctionListing = AuctionListing(),
    val bids: List<Bid> = emptyList()
)