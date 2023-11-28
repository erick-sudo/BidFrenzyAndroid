package com.draw.bidfrenzy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SpinViewModel: ViewModel() {

    private var startSpin = MutableStateFlow(false)
    val isSpinning
        get() = startSpin.asStateFlow()

    private var _angleFlow = MutableStateFlow(0f)
    val angleFlow
        get() = _angleFlow.asStateFlow()

    private fun spin() {
        viewModelScope.launch(
            Dispatchers.Main
        ) {
            while(startSpin.value) {
                delay(1000)
                _angleFlow.value += 45f
            }
        }
    }

    fun stop() {
        startSpin.value = false
    }

    fun start() {
        startSpin.value = true
        spin()
    }
}