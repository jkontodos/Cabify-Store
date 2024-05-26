package com.jkontodos.cabifystore.data.usecases

import com.jkontodos.cabifystore.data.repository.StoreRepository
import com.jkontodos.cabifystore.domain.Product
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    fun invoke(product: Product) =
        storeRepository.addProductToCart(product)
}