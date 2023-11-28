package com.draw.bidfrenzy.extensions

import java.util.Date
import kotlin.math.absoluteValue

fun Date.splitUnits(other: Date): List<Long> {

    var elapsedMilliseconds = (this.time - other.time).absoluteValue

    val divisors = listOf(
        (24 * 3600 * 1000),
        3600 * 1000,
        60 * 1000,
        1000,
        1
    )

    return divisors.map { divisor ->
        val quotient = elapsedMilliseconds/divisor
        if(quotient > 0) {
            elapsedMilliseconds -= quotient * divisor
        }
        quotient
    }
}