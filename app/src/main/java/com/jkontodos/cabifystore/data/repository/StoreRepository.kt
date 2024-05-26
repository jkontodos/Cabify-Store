package com.jkontodos.cabifystore.data.repository

import com.jkontodos.cabifystore.data.common.Either
import com.jkontodos.cabifystore.data.common.fold
import com.jkontodos.cabifystore.data.exception.Failure
import com.jkontodos.cabifystore.data.source.LocalDataSource
import com.jkontodos.cabifystore.data.source.RemoteDataSource
import com.jkontodos.cabifystore.domain.CartDiscounts
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
                val updatedProduct = getUpdatedProduct(product.toDomainCartProduct(existingProduct.quantity + 1))
                existingProduct.quantity = updatedProduct.quantity
                existingProduct.price = updatedProduct.price
                existingProduct.priceDiscount = updatedProduct.priceDiscount
            } else {
                cart.products.add(product.toDomainCartProduct(1))
            }
            cart.subTotal = getCartSubTotal(cart)
            cart.discounts = getCartDiscounts(cart)
            cart.total = getCartTotal(cart)
            localDataSource.saveCustomerCart(cart)
        }?: run {
            localDataSource.saveCustomerCart(CustomerCart(mutableListOf(product.toDomainCartProduct(1)), product.price, mutableListOf(), product.price))
        }
    }

    /**
     * Function to get the cart's subtotal price without applying discounts
     *
     * @param cart the customer's cart.
     * @return the subtotal price of the cart.
     */
    private fun getCartSubTotal(cart: CustomerCart): Double {
        var total = 0.0
        cart.products.forEach { product ->
            total += product.price
        }
        return total
    }

    /**
     * Function to list the discounts applied to the cart
     *
     * @param cart the customer's cart.
     * @return a list of discounts applied to the cart.
     */
    private fun getCartDiscounts(cart: CustomerCart): MutableList<CartDiscounts> {
        val discounts: MutableList<CartDiscounts> = mutableListOf()
        cart.products.forEach { product ->
            if (product.priceDiscount > 0.0){
                val discountName = when (product.code) {
                    "VOUCHER" -> "2x1 Promo"
                    "TSHIRT" -> "Bulk Purchase Discount"
                    else -> "Discount Promo"
                }
                discounts.add(CartDiscounts(discountName, product.price-product.priceDiscount))
            }
        }
        return discounts
    }

    /**
     * Function to get the total price of the cart after applying discounts
     *
     * @param cart the customer's cart.
     * @return the total price of the cart after applying discounts.
     */
    private fun getCartTotal(cart: CustomerCart): Double {
        var total = 0.0
        cart.products.forEach { product ->
            total += if (product.priceDiscount > 0.0){
                product.priceDiscount
            }else{
                product.price
            }
        }
        return total
    }

    /**
     * Function that updates the product in the cart based on applicable discounts
     *
     * @param product the product to update in the cart.
     * @return the updated product in the cart.
     */
    private fun getUpdatedProduct(product: CartProduct): CartProduct {
        val discountedPrice: Double = when (product.code) {
            "VOUCHER" -> {
                val freeItems = product.quantity / 2
                product.price * (product.quantity - freeItems)
            }
            "TSHIRT" -> {
                if (product.quantity >= 3){
                    val unitPrice = product.price - 1.0
                    unitPrice * product.quantity
                }else{
                    0.0
                }
            }
            else -> 0.0
        }
        return CartProduct(
            code = product.code,
            name = product.name,
            quantity = product.quantity,
            price = product.price * product.quantity,
            priceDiscount = discountedPrice
        )
    }

    /**
     * Function to retrieve the customer cart.
     *
     * @return the customer cart or null if it doesn't exist.
     */
    fun getCustomerCart(): CustomerCart? =
        localDataSource.retrieveCustomerCart()

    /** * Function to delete the customer cart. */
    fun deleteCustomerCart() =
        localDataSource.deleteCustomerCart()
}