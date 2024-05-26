package com.jkontodos.cabifystore.domain.datamappers

import com.jkontodos.cabifystore.data.server.response.ProductListResponse
import com.jkontodos.cabifystore.data.server.response.ProductResponse
import com.jkontodos.cabifystore.domain.CartProduct
import com.jkontodos.cabifystore.domain.Product

/**
 * Converts a [ProductResponse] to a list of [Product]
 *
 * @return the converted list of [Product]
 */
fun ProductListResponse.toDomainProductList(): List<Product> =
    products.map { it.toDomainProduct() }

/**
 * Converts a [ProductResponse] to a [Product]
 *
 * @return the converted [Product]
 */
fun ProductResponse.toDomainProduct(): Product = Product(code, name, price)

/**
 * Converts a [Product] to a [CartProduct]
 *
 * @return the converted [CartProduct]
 */
fun Product.toDomainCartProduct(): CartProduct = CartProduct(code, name, price, 1)