package com.dicoding.mycomposesneakers.ui.screen.cart

import com.dicoding.mycomposesneakers.model.OrderSneakers

data class CartState(
    val orderSneakers: List<OrderSneakers>,
    val totalPrice: Int
)