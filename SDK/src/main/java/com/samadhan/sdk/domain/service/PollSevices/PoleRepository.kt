package com.samadhan.sdk.domain.service.PollSevices

import com.samadhan.sdk.data.model.LoginRequest
import com.samadhan.sdk.data.model.LoginResponse

interface PoleRepository {
    suspend fun poleLogin(loginRequest: LoginRequest): LoginResponse
}