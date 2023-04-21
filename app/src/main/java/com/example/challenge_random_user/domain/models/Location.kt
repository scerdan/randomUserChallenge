package com.example.challenge_random_user.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    @SerializedName("city")
    val city: String,
    @SerializedName("coordinates")
    val coordinates: Coordinates,
    @SerializedName("country")
    val country: String,
    @SerializedName("postcode")
    val postcode: Int,
    @SerializedName("state")
    val state: String,
    @SerializedName("street")
    val street: Street,
    @SerializedName("timezone")
    val timezone: Timezone
): Serializable