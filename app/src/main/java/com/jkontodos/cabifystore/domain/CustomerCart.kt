package com.jkontodos.cabifystore.domain

data class CustomerCart (
    var products: MutableList<CartProduct>
)

data class CartProduct (
    var code: String,
    var name: String,
    var price: Double,
    var quantity: Int,
)
