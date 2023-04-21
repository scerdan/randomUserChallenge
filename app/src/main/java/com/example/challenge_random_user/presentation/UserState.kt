package com.example.challenge_random_user.presentation

import com.example.challenge_random_user.domain.models.User


data class UserState(
    val isLoading: Boolean = false,
    val users: List<com.example.challenge_random_user.domain.models.Result> = emptyList(),
    val error: String = "",
    var allUsers: List<User> = emptyList()
)