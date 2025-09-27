package com.samadhan.sdk


import android.util.Log
import com.samadhan.sdk.domain.service.UserApiService
import com.samadhan.sdk.domain.service.UserRepositoryImpl
import com.samadhan.sdk.domain.usecase.GetUserUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SdkInitializer {

    lateinit var getUserUseCase: GetUserUseCase
        private set

    fun init(baseUrl: String) {
        Log.d("SdkInitializer", "Initializing SDK with baseUrl: $baseUrl")

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(UserApiService::class.java)
        val repo = UserRepositoryImpl(api)

        getUserUseCase = GetUserUseCase(repo)

        Log.d("SdkInitializer", "SDK initialized successfully.")
    }
}
