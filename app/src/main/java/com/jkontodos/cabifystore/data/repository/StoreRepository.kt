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
    suspend fun getProducts(): Either<Failure, List<Product>> =
        remoteDataSource.getProductList().fold({
            Either.Left(it)
        }
        ) {
            Either.Right(
                it.toDomainProductList()
            )
        }
}