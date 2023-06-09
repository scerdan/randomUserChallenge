package com.example.challenge_random_user.presentation

import com.example.challenge_random_user.domain.models.Result

data class UserState(
    val isLoading: Boolean = false,
    val error: String = "",
    var allUsers: ArrayList<Result> = arrayListOf()
)