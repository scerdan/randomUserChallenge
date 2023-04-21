package com.example.challenge_random_user.data.repository

import com.example.challenge_random_user.data.remote.ApiService
import com.example.challenge_random_user.domain.models.Result
import com.example.challenge_random_user.domain.models.User
import com.example.challenge_random_user.domain.repository.UserRespository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: ApiService) : UserRespository {

    override suspend fun getRandomUser(): Response<User> {
        return api.getRandomUser()
    }
}