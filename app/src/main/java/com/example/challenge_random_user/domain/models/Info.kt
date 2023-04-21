package com.example.challenge_random_user.domain.models

data class Info(
    val page: Int,
    val results: Int,
    val seed: String,
    val version: String
)