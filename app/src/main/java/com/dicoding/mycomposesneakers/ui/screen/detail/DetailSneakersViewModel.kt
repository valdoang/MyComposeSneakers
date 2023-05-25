package com.dicoding.mycomposesneakers.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mycomposesneakers.data.SneakersRepository
import com.dicoding.mycomposesneakers.model.OrderSneakers
import com.dicoding.mycomposesneakers.model.Sneakers
import com.dicoding.mycomposesneakers.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailSneakersViewModel(private val repository: SneakersRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderSneakers>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderSneakers>>
        get() = _uiState

    fun getSneakersById(sneakersId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderSneakerById(sneakersId))
        }
    }

    fun addToCart(sneakers: Sneakers, count: Int) {
        viewModelScope.launch {
            repository.updateOrderSneaker(sneakers.id, count)
        }
    }
}