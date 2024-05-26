package com.jkontodos.cabifystore.data.sharedpreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.jkontodos.cabifystore.data.source.LocalDataSource
import javax.inject.Inject

class SharedPreferencesDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : LocalDataSource {
    companion object {
        private const val CART_COUNTER = "cart_counter"
    }

    /**
     * Saves the cart counter to shared preferences
     *
     * @param count The cart counter
     */
    override fun saveCartCounter(count: Int) = sharedPreferences.edit(commit = true) {
        putInt(CART_COUNTER, count).apply()
    }

    /**
     * Retrieves the cart counter from shared preferences
     *
     * @return The cart counter
     */
    override fun retrieveCartCounter(): Int =
        sharedPreferences.getInt(CART_COUNTER, 0)

}