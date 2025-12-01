package com.samadhan.sdk.domain.service.PollSevices

import com.samadhan.sdk.data.model.GetPollsRequest
import com.samadhan.sdk.data.model.GetPollsResponse
import com.samadhan.sdk.data.model.GetUserSelectedOptionsResponse
import com.samadhan.sdk.data.model.LoginRequest
import com.samadhan.sdk.data.model.LoginResponse
import com.samadhan.sdk.data.model.PoleDetails.PollDetailsResponse
import com.samadhan.sdk.data.model.QrRequest
import com.samadhan.sdk.data.model.SubmitPollRequest
import com.samadhan.sdk.data.model.SubmitPollResponse
import com.samadhan.sdk.data.model.VerifyPoleRequest
import com.samadhan.sdk.data.model.getUserSelectedOptions
import okhttp3.ResponseBody

interface PoleRepository {
    suspend fun poleLogin(loginRequest: LoginRequest): LoginResponse
    suspend fun getPolls(request: GetPollsRequest, token: String): GetPollsResponse
    suspend fun getPollDetails(token: String, id:Long): PollDetailsResponse
    suspend fun getUserSelectedOptions(token: String, request: getUserSelectedOptions,): List<GetUserSelectedOptionsResponse>
    suspend fun savePole(token: String, request: SubmitPollRequest): SubmitPollResponse
    suspend fun removePole(token: String, request: getUserSelectedOptions): ResponseBody
    suspend fun scanPole(token: String, request: VerifyPoleRequest): ResponseBody
    suspend fun generateQr(token: String, request: QrRequest): retrofit2.Response<ResponseBody>
}