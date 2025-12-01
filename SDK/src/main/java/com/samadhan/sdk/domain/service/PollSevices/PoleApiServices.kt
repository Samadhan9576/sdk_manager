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
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PoleApiServices {
    @POST("api/v1/auth/login")
    suspend fun poleLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("api/v1/poll/getpolls")
    suspend fun getPolls(
        @Body request: GetPollsRequest,
        @Header("Authorization") token: String
    ): GetPollsResponse

    @GET("api/v1/poll/details")
    suspend fun getPollDetails(
        @Header("Accept") accept: String = "application/json, text/plain, */*",
        @Header("Authorization") token: String,
        @Query("id") id: Long,
        ): PollDetailsResponse

    @POST("api/v1/vote/getUserSelectedOptions")
    suspend fun getUserSelectedOptions(
        @Header("Authorization") token: String,
        @Body request: getUserSelectedOptions
    ): List<GetUserSelectedOptionsResponse>

    @POST("api/v1/vote/save")
    suspend fun savePole(
        @Header("Authorization") token: String,
        @Body request: SubmitPollRequest
    ): SubmitPollResponse

    @POST("api/v1/vote/removeUserPollVotes")
    suspend fun removePole(
        @Header("Authorization") token: String,
        @Body request: getUserSelectedOptions
    ): ResponseBody

    @POST("api/v1/vote/verifyPollVote")
    suspend fun scanPole(
        @Header("Authorization") token: String,
        @Body request: VerifyPoleRequest
    ): ResponseBody

    @POST("api/v1/poll/generate-qr")
    suspend fun generateQr(
        @Header("Authorization") token: String,
        @Body request: QrRequest
    ): retrofit2.Response<ResponseBody>

}
