package com.jkontodos.cabifystore.data.usecases

import com.jkontodos.cabifystore.data.common.Either
import com.jkontodos.cabifystore.data.exception.Failure
import com.jkontodos.cabifystore.data.repository.StoreRepository
import com.jkontodos.cabifystore.domain.Product
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetProductsUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend fun invoke(): Either<Failure, List<Product>> =
        storeRepository.getProducts()
}