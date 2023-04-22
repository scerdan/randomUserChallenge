package com.example.challenge_random_user.data.remote

import com.example.challenge_random_user.domain.models.User
import com.example.challenge_random_user.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/?results=${Constants.LIMIT}")
    suspend fun getRandomUser(): Response<User>
}