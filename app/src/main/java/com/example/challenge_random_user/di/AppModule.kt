package com.example.challenge_random_user.di

import com.example.challenge_random_user.data.remote.ApiService
import com.example.challenge_random_user.data.repository.UserRepositoryImpl
import com.example.challenge_random_user.domain.repository.UserRespository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideRandomUserRepository(api: ApiService): UserRespository =
        UserRepositoryImpl(api)
}