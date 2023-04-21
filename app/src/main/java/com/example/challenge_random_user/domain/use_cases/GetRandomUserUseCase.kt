package com.example.challenge_random_user.domain.use_cases

import com.example.challenge_random_user.domain.models.User
import com.example.challenge_random_user.domain.repository.UserRespository
import com.example.challenge_random_user.utlis.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRandomUserUseCase @Inject constructor(
    private val repository: UserRespository
) {

    fun execute(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val users = repository.getRandomUser()
            emit(Resource.Success(users))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}