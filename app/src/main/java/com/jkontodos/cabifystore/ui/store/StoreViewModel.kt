package com.jkontodos.cabifystore.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkontodos.cabifystore.data.exception.Failure
import com.jkontodos.cabifystore.data.usecases.GetProductsUseCase
import com.jkontodos.cabifystore.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private var _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model

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

    private fun handleSuccessProductList(productList: List<Product>) {
        _model.postValue(UiModel.SuccessProducts(productList))
    }

    private fun handleFailure(failure: Failure) {
        _model.postValue(
            when (failure) {
                is Failure.ServerError -> UiModel.Failure.ServerError
                is Failure.UnauthorizedError -> UiModel.Failure.UnauthorizedError
                is Failure.Exception -> UiModel.Failure.ServerErrorWithException
            }
        )
    }

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