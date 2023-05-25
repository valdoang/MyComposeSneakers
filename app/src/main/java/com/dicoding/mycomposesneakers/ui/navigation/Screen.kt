package com.dicoding.mycomposesneakers.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object About : Screen("about")
    object DetailSneakers : Screen("home/{sneakersId}") {
        fun createRoute(sneakersId: Long) = "home/$sneakersId"
    }
}