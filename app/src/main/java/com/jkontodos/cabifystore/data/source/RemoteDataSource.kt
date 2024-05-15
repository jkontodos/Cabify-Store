package com.jkontodos.cabifystore.data.source

import com.jkontodos.cabifystore.data.common.Either
import com.jkontodos.cabifystore.data.exception.Failure
import com.jkontodos.cabifystore.data.server.response.ProductListResponse

interface RemoteDataSource {
    suspend fun getProductList(): Either<Failure, ProductListResponse>
}