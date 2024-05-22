package com.jkontodos.cabifystore.data.server

import android.util.Log
import com.jkontodos.cabifystore.data.common.Either
import com.jkontodos.cabifystore.data.exception.Failure
import com.jkontodos.cabifystore.data.server.response.ProductListResponse
import com.jkontodos.cabifystore.data.source.RemoteDataSource
import javax.inject.Inject

class StoreDataSource @Inject constructor(
    private val storeService: StoreService
) : RemoteDataSource {

    /**
     * Returns an iterator with the list of available products or a service error by calling the getProducts() function from StoreService.
     * Based on the response, it will return either the service's data model or an error from the sealed class Failure.
     *
     * @return Either<Failure, ProductListResponse> an iterator with the available product list response or a service error.
     */
    override suspend fun getProductList(): Either<Failure, ProductListResponse> =
        try {
            val palcalde = "6c19259bd32dd6aafa327fa557859c2f"
            val raw = "ba51779474a150ee4367cda4f4ffacdcca479887"
            val response = storeService.getProducts(palcalde, raw)

            when {
                response.isSuccessful -> {
                    response.body()?.let {
                        Either.Right(it)
                    } ?: Either.Left(Failure.ServerError)
                }
                response.code() == 401 -> {
                    Either.Left(Failure.UnauthorizedError)
                }
                else -> {
                    Either.Left(Failure.ServerError)
                    throw RuntimeException("Request: ${response.raw().request}. Error retrieving data (Code ${response.code()}). Raw response: ${response.raw()}")
                }
            }
        } catch (exception: Exception) {
            Log.e("StoreDataSource", "ApiError: ${exception.localizedMessage}")
            Either.Left(Failure.Exception(exception))
        }
}