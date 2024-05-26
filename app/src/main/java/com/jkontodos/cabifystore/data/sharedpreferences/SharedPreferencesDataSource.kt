package com.jkontodos.cabifystore.data.sharedpreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.jkontodos.cabifystore.data.source.LocalDataSource
import com.jkontodos.cabifystore.domain.CustomerCart
import javax.inject.Inject

class SharedPreferencesDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : LocalDataSource {
    companion object {
        private const val CART_COUNTER = "cart_counter"
        private const val CUSTOMER_CART = "customer_cart"
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

    /**
     * Saves the customer cart to shared preferences
     *
     * @param customerCart The customer cart
     */
    override fun saveCustomerCart(customerCart: CustomerCart) =
        sharedPreferences.edit(commit = true) {
            putString(
                CUSTOMER_CART,
                gson.toJson(customerCart, CustomerCart::class.java)
            ).apply()
        }

    /**
     * Retrieves the customer cart from shared preferences
     *
     * @return The customer cart
     */
    override fun retrieveCustomerCart(): CustomerCart? =
        sharedPreferences.getString(CUSTOMER_CART, null)?.let {
            gson.fromJson(it, CustomerCart::class.java)
        }

    /** * Deletes the customer cart from shared preferences */
    override fun deleteCustomerCart() = sharedPreferences.edit(commit = true) {
        remove(CUSTOMER_CART).apply()
    }

}