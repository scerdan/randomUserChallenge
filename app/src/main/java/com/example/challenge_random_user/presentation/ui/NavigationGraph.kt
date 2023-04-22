package com.example.challenge_random_user.presentation.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challenge_random_user.presentation.viewmodels.UserViewModel
import com.example.challenge_random_user.utils.Screen
import com.example.challenge_random_user.utils.UserMainScreen


@ExperimentalMaterialApi
@Composable
fun NavigationGraph(viewModel: UserViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SPLASH.route) {

        composable(route = Screen.SPLASH.route) {
            SplashScreen(navController)
        }

        composable(route = Screen.HOME_SCREEN.route) {
            UserMainScreen(viewModel, navController)
        }

        composable(route = Screen.DETAIL_SCREEN.route) {
            DetailScreen()
        }

    }
}

