package com.dicoding.mycomposesneakers.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mycomposesneakers.data.SneakersRepository
import com.dicoding.mycomposesneakers.model.OrderSneakers
import com.dicoding.mycomposesneakers.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: SneakersRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderSneakers>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderSneakers>>>
        get() = _uiState

    fun getAllSneakers() {
        viewModelScope.launch {
            repository.getAllSneakers()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderSneakers ->
                    _uiState.value = UiState.Success(orderSneakers)
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        viewModelScope.launch{
            _query.value = newQuery
            _uiState.value = UiState.Success(repository.searchSneakers(_query.value))
        }
    }
}