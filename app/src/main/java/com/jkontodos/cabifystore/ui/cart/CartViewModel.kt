package com.jkontodos.cabifystore.ui.cart

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkontodos.cabifystore.data.usecases.GetCartCounterUseCase
import com.jkontodos.cabifystore.data.usecases.SaveCartCounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val saveCartCounterUseCase: SaveCartCounterUseCase,
    private val getCartCounterUseCase: GetCartCounterUseCase
) : ViewModel() {
    val cartCounter: ObservableField<Int?> = ObservableField()

    /** * Gets the number of products in the shopping cart to display in the counter. */
    fun getCartCount() {
        viewModelScope.launch {
            cartCounter.set(getCartCounterUseCase.invoke())
        }
    }

    /** * Resets the number of products in the shopping cart to 0. */
    private fun resetCartCount() {
        viewModelScope.launch {
            saveCartCounterUseCase.invoke(0)
            cartCounter.set(0)
        }
    }
}