package com.example.challenge_random_user.domain.repository

import com.example.challenge_random_user.domain.models.Result

interface UserRespository {

    suspend fun getRandomUser(): Result

}