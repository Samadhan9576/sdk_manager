package com.samadhan.sdk.di

import com.google.gson.GsonBuilder
import com.samadhan.sdk.SdkInitializer
import com.samadhan.sdk.domain.service.PollSevices.PoleApiServices
import com.samadhan.sdk.domain.service.PollSevices.PoleRepository
import com.samadhan.sdk.domain.service.PollSevices.PoleRepositoryImpl
import com.samadhan.sdk.domain.service.UserApiService
import com.samadhan.sdk.domain.service.UserRepository
import com.samadhan.sdk.domain.service.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SdkModule {

    private val loggingInterceptor = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun providesRegistartionPostApi(): UserApiService {
        val baseUrl = SdkInitializer.baseUrl
        val gson = GsonBuilder()
            .disableHtmlEscaping()
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(api: UserApiService): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesPoleService(): PoleApiServices {
        val baseUrl = SdkInitializer.poleBaseUrl
        val gson = GsonBuilder()
            .disableHtmlEscaping()
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(PoleApiServices::class.java)
    }

    @Provides
    @Singleton
    fun providePoleServices(api: PoleApiServices): PoleRepository {
        return PoleRepositoryImpl(api)
    }
}
