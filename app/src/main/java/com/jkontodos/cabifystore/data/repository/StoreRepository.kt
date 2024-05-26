package com.jkontodos.cabifystore.data.repository

import com.jkontodos.cabifystore.data.common.Either
import com.jkontodos.cabifystore.data.common.fold
import com.jkontodos.cabifystore.data.exception.Failure
import com.jkontodos.cabifystore.data.source.LocalDataSource
import com.jkontodos.cabifystore.data.source.RemoteDataSource
import com.jkontodos.cabifystore.domain.Product
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
}