package com.example.challenge_random_user.domain.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Result>
)