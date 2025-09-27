package com.samadhan.sdk.domain.service

import com.samadhan.sdk.data.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): UserDto
}