package com.example.challenge_random_user.domain.repository

import com.example.challenge_random_user.domain.models.User
import retrofit2.Response

interface UserRepository {
    suspend fun getRandomUser(pageCount: Int): Response<User>
}