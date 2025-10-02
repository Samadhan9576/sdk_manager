package com.samadhan.sdk.domain.service.PollSevices

import com.samadhan.sdk.data.model.GetPollsRequest
import com.samadhan.sdk.data.model.GetPollsResponse
import com.samadhan.sdk.data.model.LoginRequest
import com.samadhan.sdk.data.model.LoginResponse

interface PoleRepository {
    suspend fun poleLogin(loginRequest: LoginRequest): LoginResponse
    suspend fun getPolls(request: GetPollsRequest, token: String): GetPollsResponse
}