package com.example.challenge_random_user.utils

sealed class Screen(val route: String) {
    object SPLASH : Screen("SplashScreen")
    object HOME_SCREEN : Screen("UserMainScreen")
    object DETAIL_SCREEN : Screen("DetailScreen")
}
