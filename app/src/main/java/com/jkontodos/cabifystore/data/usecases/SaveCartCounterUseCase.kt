package com.jkontodos.cabifystore.data.usecases

import com.jkontodos.cabifystore.data.repository.StoreRepository
import javax.inject.Inject

class SaveCartCounterUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    fun invoke(count: Int) =
        storeRepository.saveCartCounter(count)
}