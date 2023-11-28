package com.draw.bidfrenzy.models

import java.util.Date

data class Order(
    val date: Date = Date(),
    val shippingAddress: ShippingAddress? = null,
    val modeOfPayment: ModeOfPayment = ModeOfPayment.Mpesa,
    val paymentPolicy: PaymentPolicy = PaymentPolicy.PayOnDelivery
) {

    val shippingFee
        get() = 179.0
}