package com.jkontodos.cabifystore.data.usecases

import com.jkontodos.cabifystore.data.repository.StoreRepository
import javax.inject.Inject

class GetCartCounterUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    fun invoke(): Int =
        storeRepository.getCartCounter()
}