package com.example.challenge_random_user.di

import com.example.challenge_random_user.data.remote.ApiService
import com.example.challenge_random_user.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.newBuilder().build())
            .build()
    }

    @Provides
    @Singleton
    fun provideRandomUserApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}