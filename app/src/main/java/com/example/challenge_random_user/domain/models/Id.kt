package com.example.challenge_random_user.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Id(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("value")
    val value: String? = ""
): Serializable