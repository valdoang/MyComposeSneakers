package com.dicoding.mycomposesneakers.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mycomposesneakers.data.SneakersRepository
import com.dicoding.mycomposesneakers.ui.screen.cart.CartViewModel
import com.dicoding.mycomposesneakers.ui.screen.detail.DetailSneakersViewModel
import com.dicoding.mycomposesneakers.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: SneakersRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailSneakersViewModel::class.java)) {
            return DetailSneakersViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}