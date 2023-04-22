package com.example.challenge_random_user.utils

import androidx.navigation.NavController
import com.example.challenge_random_user.BuildConfig

class Constants {

    companion object {
        const val LIMIT = "20"
        const val BASEURL = BuildConfig.BASE_URL

        fun goTo(direction: String, navController: NavController, offset: Boolean) {
            if (offset) {
                navController.popBackStack()
            }
            navController.navigate(direction)
        }
    }
}