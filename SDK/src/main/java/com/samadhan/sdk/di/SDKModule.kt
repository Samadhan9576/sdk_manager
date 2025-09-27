package com.samadhan.sdk.di

import android.util.Log
import com.samadhan.sdk.SdkInitializer
import com.samadhan.sdk.domain.service.UserApiService
import com.samadhan.sdk.domain.service.UserRepository
import com.samadhan.sdk.domain.service.UserRepositoryImpl
import com.samadhan.sdk.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// In SDK module
@Module
@InstallIn(SingletonComponent::class)
object SdkModule {

    @Provides
    fun provideBaseUrl(): String {
        // Default base URL or throw error if not initialized
        return SdkInitializer.baseUrl ?: throw IllegalStateException("SDK not initialized")
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor { Log.d("SDK", it) }
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideUserApiService(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    fun provideUserRepository(api: UserApiService): UserRepository =
        UserRepositoryImpl(api)

    @Provides
    fun provideGetUserUseCase(repository: UserRepository): GetUserUseCase =
        GetUserUseCase(repository)
}
