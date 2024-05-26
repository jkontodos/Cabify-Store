package com.jkontodos.cabifystore.data.source

interface LocalDataSource {

    /** * Cart counter preferences */
    fun saveCartCounter(count: Int)
    fun retrieveCartCounter(): Int
}