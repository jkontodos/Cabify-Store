package com.jkontodos.cabifystore.data.usecases

import com.jkontodos.cabifystore.data.repository.StoreRepository
import com.jkontodos.cabifystore.domain.CustomerCart
import javax.inject.Inject

class GetCustomerCartUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    fun invoke(): CustomerCart? =
        storeRepository.getCustomerCart()
}