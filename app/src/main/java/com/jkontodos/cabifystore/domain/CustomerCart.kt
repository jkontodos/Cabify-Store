package com.jkontodos.cabifystore.domain

data class CustomerCart (
    var products: MutableList<CartProduct>,
    var subTotal: Double,
    var discounts: MutableList<CartDiscounts>,
    var total: Double
)

data class CartProduct (
    var code: String,
    var name: String,
    var price: Double,
    var priceDiscount: Double,
    var quantity: Int,
)
data class CartDiscounts (
    var name: String,
    var price: Double
)
