package com.draw.bidfrenzy

import android.app.Application
import com.draw.bidfrenzy.repository.PreferencesRepository

class BidFrenzyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        PreferencesRepository.initialize(this)
    }
}