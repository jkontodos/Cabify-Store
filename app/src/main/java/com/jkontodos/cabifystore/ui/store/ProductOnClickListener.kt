package com.jkontodos.cabifystore.ui.store

import com.jkontodos.cabifystore.domain.Product

interface ProductOnClickListener {
    fun onAddToCartProduct(product: Product)
}