package com.samadhan.sdk.domain.service.PollSevices

import com.samadhan.sdk.data.model.GetPollsRequest
import com.samadhan.sdk.data.model.GetPollsResponse
import com.samadhan.sdk.data.model.GetUserSelectedOptionsResponse
import com.samadhan.sdk.data.model.LoginRequest
import com.samadhan.sdk.data.model.LoginResponse
import com.samadhan.sdk.data.model.PoleDetails.PollDetailsResponse
import com.samadhan.sdk.data.model.SubmitPollRequest
import com.samadhan.sdk.data.model.SubmitPollResponse
import com.samadhan.sdk.data.model.getUserSelectedOptions
import okhttp3.ResponseBody

class PoleRepositoryImpl(
    private val api: PoleApiServices
) : PoleRepository {
    override suspend fun poleLogin(loginRequest: LoginRequest): LoginResponse {
        return api.poleLogin(loginRequest)
    }

    override suspend fun getPolls(request: GetPollsRequest, token: String): GetPollsResponse {
        return api.getPolls(request,token)
    }
    override suspend fun getPollDetails(token: String, id: Long): PollDetailsResponse {
        return api.getPollDetails(token = token,id = id)
     }

    override suspend fun getUserSelectedOptions(
        token: String,
        request: getUserSelectedOptions
    ): List<GetUserSelectedOptionsResponse> {
        return api.getUserSelectedOptions(token,request)
    }

    override suspend fun savePole(token: String, request: SubmitPollRequest): SubmitPollResponse {
       return api.savePole(token,request)
    }

    override suspend fun removePole(token: String, request: getUserSelectedOptions): ResponseBody {
        return api.removePole(token,request)
    }
}