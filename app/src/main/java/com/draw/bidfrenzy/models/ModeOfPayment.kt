package com.draw.bidfrenzy.models

import androidx.annotation.DrawableRes
import com.draw.bidfrenzy.R

enum class ModeOfPayment(
    @DrawableRes icon: Int
) {
    Mpesa(R.drawable.baseline_currency_bitcoin_24),
    Paypal(R.drawable.baseline_currency_bitcoin_24),
    CreditCard(R.drawable.baseline_currency_bitcoin_24),
    Skrill(R.drawable.baseline_currency_bitcoin_24),
    Pioneer(R.drawable.baseline_currency_bitcoin_24),
    Bitcoin(R.drawable.baseline_currency_bitcoin_24);

    val icon = icon
}