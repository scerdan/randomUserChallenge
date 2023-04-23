package com.example.challenge_random_user.data.remote

import com.example.challenge_random_user.domain.models.User
import com.example.challenge_random_user.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun getRandomUser(@Query("results") numberOfData: String = Constants.NO_RECORDS_PER_REQUEST): Response<User>
}