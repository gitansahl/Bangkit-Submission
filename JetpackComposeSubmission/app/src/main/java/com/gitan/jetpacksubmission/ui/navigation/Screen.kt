package com.gitan.jetpacksubmission.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Profile: Screen("profile")
    object DetailCast: Screen("home/{castId}") {
        fun createRoute(castId: Long) = "home/$castId"
    }
}
