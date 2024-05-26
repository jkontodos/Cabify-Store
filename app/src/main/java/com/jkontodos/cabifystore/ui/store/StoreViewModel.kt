package com.jkontodos.cabifystore.ui.store

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkontodos.cabifystore.data.exception.Failure
import com.jkontodos.cabifystore.data.usecases.AddProductToCartUseCase
import com.jkontodos.cabifystore.data.usecases.GetCartCounterUseCase
import com.jkontodos.cabifystore.data.usecases.GetProductsUseCase
import com.jkontodos.cabifystore.data.usecases.SaveCartCounterUseCase
import com.jkontodos.cabifystore.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val saveCartCounterUseCase: SaveCartCounterUseCase,
    private val getCartCounterUseCase: GetCartCounterUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
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

    /** * Gets the list of products from the server. */
    fun getProducts() {
        _model.value = UiModel.Loading
        viewModelScope.launch {
            delay(1000)
            val response = getProductsUseCase.invoke()
            response.either({
                handleFailure(it)
            }, {
                handleSuccessProductList(it)
            })
        }
    }

    /** 
     * Adds a product to the shopping cart.
     * 
     * @param product The product to add to the shopping cart.
     */
    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            addProductToCartUseCase.invoke(product)
            saveCartCount()
        }
    }
    
    /** * Saves the number of products in the shopping cart. */
    private fun saveCartCount() {
        viewModelScope.launch {
            var count = cartCounter.get() ?: 0
            count++
            saveCartCounterUseCase.invoke(count)
            cartCounter.set(count)
        }
    }

    /**
     * Handles the success response from the server.
     * 
     * @param productList The list of products from the server.
     */
    private fun handleSuccessProductList(productList: List<Product>) {
        _model.postValue(UiModel.SuccessProducts(productList))
    }

    /** 
     * Handles the failure response from the server.
     * 
     * @param failure The failure response from the server.
     */
    private fun handleFailure(failure: Failure) {
        _model.postValue(
            when (failure) {
                is Failure.ServerError -> UiModel.Failure.ServerError
                is Failure.UnauthorizedError -> UiModel.Failure.UnauthorizedError
                is Failure.Exception -> UiModel.Failure.ServerErrorWithException
            }
        )
    }

    /** * Represents the UI model. */
    sealed class UiModel {
        data object Loading : UiModel()
        data class Failure(val message: String? = null) : UiModel() {
            data object ServerError : UiModel()
            data object UnauthorizedError : UiModel()
            data object ServerErrorWithException : UiModel()
        }
        data class SuccessProducts(val productList: List<Product>) : UiModel()
    }
}