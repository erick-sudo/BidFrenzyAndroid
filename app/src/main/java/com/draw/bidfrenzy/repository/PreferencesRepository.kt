package com.draw.bidfrenzy.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PreferencesRepository private constructor(
    private val dataStore: DataStore<Preferences>
) {

    val preferredShippingAddressId: Flow<String> = dataStore.data.map {
        it[PREF_SHIPPING_ADDRESS_ID] ?: ""
    }.distinctUntilChanged()

    private var _isRememberingShippingAddress: Flow<Boolean> = dataStore.data.map {
        it[PREF_REMEMBER_SHIPPING_ADDRESS] ?: false
    }

    val isRememberingShippingAddress
        get() = _isRememberingShippingAddress

    suspend fun setRememberShippingAddress(remember: Boolean) {
        dataStore.edit {
            it[PREF_REMEMBER_SHIPPING_ADDRESS] = remember
        }
    }

    suspend fun setPreferredShippingAddress(shippingAddressId: String) {
        dataStore.edit {
            it[PREF_SHIPPING_ADDRESS_ID] = shippingAddressId
        }
    }

    companion object {

        private val PREF_SHIPPING_ADDRESS_ID = stringPreferencesKey("preferred_shipping_address")

        private val PREF_REMEMBER_SHIPPING_ADDRESS = booleanPreferencesKey("remember_shipping_address")

        private var INSTANCE: PreferencesRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile("settings")
                }

                INSTANCE = PreferencesRepository(dataStore)
            }
        }

        fun get() : PreferencesRepository {
            return INSTANCE ?: throw IllegalStateException(
                "Preferences must be initialized"
            )
        }
    }
}