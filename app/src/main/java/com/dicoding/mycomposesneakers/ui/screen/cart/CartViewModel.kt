package com.dicoding.mycomposesneakers.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mycomposesneakers.data.SneakersRepository
import com.dicoding.mycomposesneakers.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: SneakersRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderSneakers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderSneakers()
                .collect { orderSneakers ->
                    val totalPrice =
                        orderSneakers.sumOf { it.sneakers.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderSneakers, totalPrice))
                }
        }
    }

    fun updateOrderReward(sneakersId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderSneaker(sneakersId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderSneakers()
                    }
                }
        }
    }
}