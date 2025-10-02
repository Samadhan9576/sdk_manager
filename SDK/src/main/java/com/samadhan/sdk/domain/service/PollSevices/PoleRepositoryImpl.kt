package com.samadhan.sdk.domain.service.PollSevices

import com.samadhan.sdk.data.model.GetPollsRequest
import com.samadhan.sdk.data.model.GetPollsResponse
import com.samadhan.sdk.data.model.LoginRequest
import com.samadhan.sdk.data.model.LoginResponse

class PoleRepositoryImpl(
    private val api: PoleApiServices
) : PoleRepository {
    override suspend fun poleLogin(loginRequest: LoginRequest): LoginResponse {
        return api.poleLogin(loginRequest)
    }

    override suspend fun getPolls(request: GetPollsRequest, token: String): GetPollsResponse {
        return api.getPolls(request,token)
    }
}