package com.draw.bidfrenzy.extensions

import android.icu.text.NumberFormat

val CURRENCY_FORMATTER_INSTANCE: NumberFormat by lazy {
    NumberFormat.getCurrencyInstance()
}

fun Double.toCurrency(): String {
    return CURRENCY_FORMATTER_INSTANCE.format(this)
}