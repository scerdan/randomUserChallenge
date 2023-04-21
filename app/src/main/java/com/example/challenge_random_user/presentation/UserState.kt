package com.example.challenge_random_user.presentation


data class UserState(
    val isLoading: Boolean = false,
    val error: String = "",
    var allUsers: ArrayList<com.example.challenge_random_user.domain.models.Result> = arrayListOf()
)