package com.jkontodos.cabifystore.data.repository

import com.jkontodos.cabifystore.data.common.Either
import com.jkontodos.cabifystore.data.common.fold
import com.jkontodos.cabifystore.data.exception.Failure
import com.jkontodos.cabifystore.data.source.LocalDataSource
import com.jkontodos.cabifystore.data.source.RemoteDataSource
import com.jkontodos.cabifystore.domain.CartProduct
import com.jkontodos.cabifystore.domain.CustomerCart
import com.jkontodos.cabifystore.domain.Product
import com.jkontodos.cabifystore.domain.datamappers.toDomainCartProduct
import com.jkontodos.cabifystore.domain.datamappers.toDomainProductList
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    /**
     * Function to request the product list.
     *
     * @return Either<Failure, List<Product>> an iterator with the list of available products or a service error.
     */
    suspend fun getProducts(): Either<Failure, List<Product>> =
        remoteDataSource.getProductList().fold({
            Either.Left(it)
        }
        ) {
            Either.Right(
                it.toDomainProductList()
            )
        }

    /**
     * Function to save the cart counter.
     *
     * @param count the number of products in the cart.
     */
    fun saveCartCounter(count: Int) {
        localDataSource.saveCartCounter(count)
    }

    /**
     * Function to retrieve the cart counter.
     *
     * @return the number of products in the cart.
     */
    fun getCartCounter(): Int {
        return localDataSource.retrieveCartCounter()
    }

    /**
     * Function to add a product to the cart.
     * First, it retrieves the customer's cart and then checks if the product already exists in the cart to add a new one or increase its quantity.
     * If there is no existing cart, it will create a new one.
     *
     * @param product the product to add to the cart.
     */
    fun addProductToCart(product: Product) {
        val cart = getCustomerCart()
        cart?.let {
            val existingProduct = cart.products.find { it.code == product.code }
            if (existingProduct != null) {
                existingProduct.quantity += 1
            } else {
                cart.products.add(product.toDomainCartProduct())
            }
            localDataSource.saveCustomerCart(cart)
        }?: run {
            localDataSource.saveCustomerCart(CustomerCart(mutableListOf(product.toDomainCartProduct())))
        }
    }

    /**
     * Function to retrieve the customer cart.
     *
     * @return the customer cart or null if it doesn't exist.
     */
    fun getCustomerCart(): CustomerCart? =
        localDataSource.retrieveCustomerCart()
}