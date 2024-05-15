package com.jkontodos.cabifystore.domain.datamappers

import com.jkontodos.cabifystore.data.server.response.ProductListResponse
import com.jkontodos.cabifystore.data.server.response.ProductResponse
import com.jkontodos.cabifystore.domain.Product

fun ProductListResponse.toDomainProductList(): List<Product> =
    products.map { it.toDomainProduct() }

fun ProductResponse.toDomainProduct(): Product = Product(code, name, price)