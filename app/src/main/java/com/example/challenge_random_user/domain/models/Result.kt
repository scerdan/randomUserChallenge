package com.example.challenge_random_user.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    @SerializedName("cell")
    val cell: String,
    @SerializedName("dob")
    val dob: Dob,
    @SerializedName("email")
    val email: String,
    @SerializedName("login")
    val login: Login,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Id,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: Name,
    @SerializedName("nat")
    val nat: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("picture")
    val picture: Picture
): Serializable