package com.samadhan.sdk.domain.service.PollSevices

import com.samadhan.sdk.data.model.LoginRequest
import com.samadhan.sdk.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PoleApiServices {
    @POST("api/v1/auth/login")
    suspend fun poleLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}
