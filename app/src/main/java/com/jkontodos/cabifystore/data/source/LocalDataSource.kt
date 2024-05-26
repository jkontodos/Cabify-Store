package com.jkontodos.cabifystore.data.source

import com.jkontodos.cabifystore.domain.CustomerCart

interface LocalDataSource {

    /** * Cart counter preferences */
    fun saveCartCounter(count: Int)
    fun retrieveCartCounter(): Int

    /** * Customer Cart preferences */
    fun saveCustomerCart(customerCart: CustomerCart)
    fun retrieveCustomerCart(): CustomerCart?
    fun deleteCustomerCart()
}