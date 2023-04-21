package com.example.challenge_random_user.data.repository

import com.example.challenge_random_user.domain.models.Result
import com.example.challenge_random_user.domain.repository.UserRespository

class UserRepositoryImpl: UserRespository {

    override suspend fun getRandomUser(): Result {
        TODO("Not yet implemented")
    }
}