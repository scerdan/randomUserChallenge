package com.example.challenge_random_user.data.repository

import com.example.challenge_random_user.data.remote.ApiService
import com.example.challenge_random_user.domain.models.User
import com.example.challenge_random_user.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: ApiService) : UserRepository {

    override suspend fun getRandomUser(pageCount: Int): Response<User> {
        return api.getRandomUser()
    }
}