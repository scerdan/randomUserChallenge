package com.example.challenge_random_user.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Name(
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String,
    @SerializedName("title")
    val title: String
): Serializable