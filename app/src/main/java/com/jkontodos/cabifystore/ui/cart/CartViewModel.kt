package com.jkontodos.cabifystore.ui.cart

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkontodos.cabifystore.data.usecases.DeleteCustomerCartUseCase
import com.jkontodos.cabifystore.data.usecases.GetCartCounterUseCase
import com.jkontodos.cabifystore.data.usecases.GetCustomerCartUseCase
import com.jkontodos.cabifystore.data.usecases.SaveCartCounterUseCase
import com.jkontodos.cabifystore.domain.CustomerCart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val saveCartCounterUseCase: SaveCartCounterUseCase,
    private val getCartCounterUseCase: GetCartCounterUseCase,
    private val getCustomerCartUseCase: GetCustomerCartUseCase,
    private val deleteCustomerCartUseCase: DeleteCustomerCartUseCase
) : ViewModel() {
    private var _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model

    val cartCounter: ObservableField<Int?> = ObservableField()

    /** * Gets the number of products in the shopping cart to display in the counter. */
    fun getCartCount() {
        viewModelScope.launch {
            cartCounter.set(getCartCounterUseCase.invoke())
        }
    }

    /** * Resets the shopping cart. */
    fun resetCustomerCart() {
        viewModelScope.launch {
            deleteCustomerCartUseCase.invoke()
            saveCartCounterUseCase.invoke(0)
        }
    }

    /** * Retrieves the customer's cart stored locally. */
    fun getCustomerCart() {
        viewModelScope.launch {
            val response = getCustomerCartUseCase.invoke()
            _model.value = UiModel.SuccessCustomerCart(response)
        }
    }

    /** * Represents the UI model. */
    sealed class UiModel {
        data object Loading : UiModel()
        data class Failure(val message: String? = null) : UiModel() {
            data object ServerError : UiModel()
            data object UnauthorizedError : UiModel()
            data object ServerErrorWithException : UiModel()
        }
        data class SuccessCustomerCart(val customerCart: CustomerCart?) : UiModel()
    }
}