package com.draw.bidfrenzy.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.draw.bidfrenzy.models.CartItem
import com.draw.bidfrenzy.models.ModeOfPayment
import com.draw.bidfrenzy.models.Order
import com.draw.bidfrenzy.models.PaymentPolicy
import com.draw.bidfrenzy.models.ShippingAddress
import com.draw.bidfrenzy.repository.PreferencesRepository
import com.draw.bidfrenzy.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val productRepository = ProductRepository()
    private val preferencesRepository = PreferencesRepository.get()

    private var _cartStateFlow = MutableStateFlow(CartUiState())
    private var _shippingAddressStateFlow = MutableStateFlow<List<ShippingAddress>>(emptyList())

    val isRememberingShippingAddress = preferencesRepository.isRememberingShippingAddress

    val cartStateFlow
        get() = _cartStateFlow.asStateFlow()

    val shippingAddressStateFlow
        get() = _shippingAddressStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
           preferencesRepository.isRememberingShippingAddress.collectLatest {  remember ->
               if(remember) {
                   preferencesRepository.preferredShippingAddressId.collectLatest { addressId ->
                       if(addressId.isNotEmpty()) {
                           val address = fetchShippingAddress(addressId)

                           address?.let {  newAddress ->
                               updateOrder(newAddress)
                           }
                       }
                   }
               }
           }
        }

        viewModelScope.launch {
            try {
                _cartStateFlow.update { cartUiState ->
                    // Fetch User's Cart from the API
                    cartUiState.copy(
                        cartItems = ProductRepository().fetchUsersCart()
                    )
                }
            } catch (e: Exception) {
                Log.d("CartViewModel", "${e.message}")
            }
        }
    }

    suspend fun toggleRememberShippingAddress(remember: Boolean) {
        preferencesRepository.setRememberShippingAddress(remember)
    }

    suspend fun setPreferredShippingAddress(shippingAddressId: String) {
        preferencesRepository.setPreferredShippingAddress(shippingAddressId)
    }

    private suspend fun fetchShippingAddress(addressId: String): ShippingAddress? {
        try {
            if(addressId.isNotEmpty()) {
                return productRepository.fetchShippingAddress(addressId)
            }
        } catch (ex: Exception) {
            Log.d("CartViewModel", "Failed to fetch preferred shipping address")
        }

        return null
    }

    fun fetchShippingAddresses(search: String) {
        viewModelScope.launch {
            try {
                _shippingAddressStateFlow.value = ProductRepository().fetchShippingAddresses(search)
            } catch (e: Exception) {
                Log.d("CartViewModel", "${e.message}")
            }
        }
    }

    fun clearShippingAddresses() {
        _shippingAddressStateFlow.value = emptyList()
    }

    fun updateCart(productId: String, offset: Int) {
        viewModelScope.launch {
            if(cartStateFlow.value.cartItems.find { item -> item.productId == productId } != null) {
                val newCartItems = cartStateFlow.value.cartItems.map {
                    if(it.productId == productId) {
                        it.copy(
                            quantity = it.quantity + offset
                        )
                    } else {
                        it
                    }
                }

                _cartStateFlow.update {
                    it.copy(cartItems = newCartItems)
                }
            }
        }
    }

    fun updateCart(cartItem: CartItem) {
        _cartStateFlow.update { cartUiState ->
            val previousCartItems = cartUiState.cartItems
            cartUiState.copy(
                cartItems = previousCartItems + cartItem
            )
        }
    }

    fun updateOrder(payment: ModeOfPayment) {
        _cartStateFlow.update { cartUiState ->
            cartUiState.copy(
                order = cartUiState.order?.copy(
                    modeOfPayment = payment
                )
            )
        }
    }

    fun updateOrder(paymentPolicy: PaymentPolicy) {
        _cartStateFlow.update { cartUiState ->
            cartUiState.copy(
                order = cartUiState.order?.copy(
                    paymentPolicy = paymentPolicy
                )
            )
        }
    }

    fun updateOrder(shippingAddress: ShippingAddress) {
        _cartStateFlow.update { cartUiState ->
            cartUiState.copy(
                order = cartUiState.order?.copy(
                    shippingAddress = shippingAddress
                ) ?: Order(shippingAddress = shippingAddress)
            )
        }
    }
}

data class CartUiState(
    var cartItems: List<CartItem> = emptyList(),
    var order: Order? = null
) {
    val totalPrice
    get() = cartItems.fold(0.0) { acc, curr -> acc + curr.price }

    val vat
        get() = 0.017 * totalPrice

    val totalPay
        get() = totalPrice + vat + (order?.shippingFee ?: 0.0)
}

//ShippingAddress(id="645700529982996130356229139534132296", name="Altenwerth Gardens", country="Morocco", county="Spencer", subCounty="Lakita", streetAddress="890 Nader Trace", streetAddress2="Suite 135"),
//ShippingAddress(id="850827820869826235846721671319854966", name="Sherman Turnpike", country="Solomon", county="Marc", subCounty="Alfreda", streetAddress="30591 Blanda Junctions", streetAddress2="Suite 277"),
//ShippingAddress(id="247486989000715209152112896484847484", name="Luna Stravenue", country="Brunei", county="Little", subCounty="Botsford", streetAddress="4790 Julianne Trail", streetAddress2="Apt. 936"),
//ShippingAddress(id="193754004239211779206182554195248802", name="Donn Grove", country="Tunisia", county="Marquardt", subCounty="Hermann", streetAddress="57792 Schaefer Trace", streetAddress2="Suite 622"),
//ShippingAddress(id="317297573218111345413984585286397355", name="Enola Circle", country="Nicaragua", county="Lashell", subCounty="Guillermo", streetAddress="6900 Santo Orchard", streetAddress2="Apt. 754"),
//ShippingAddress(id="155607146488192486471008683495003456", name="Florentino Trail", country="Jordan", county="Walter", subCounty="Tanika", streetAddress="627 Sherrell Villages", streetAddress2="Suite 951"),
//ShippingAddress(id="243256026003722365862590508190286470", name="Jenkins Mountain", country="Slovenia", county="Daniella", subCounty="Gerhold", streetAddress="3962 Corwin Via", streetAddress2="Apt. 257"),
//ShippingAddress(id="204742239915779845274315084714927534", name="Becker Plaza", country="Iraq", county="Sharie", subCounty="Sixta", streetAddress="184 Funk Green", streetAddress2="Suite 816")