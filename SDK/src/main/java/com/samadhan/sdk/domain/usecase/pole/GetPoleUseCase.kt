package com.samadhan.sdk.domain.usecase.pole

import com.samadhan.sdk.data.model.GetPollsRequest
import com.samadhan.sdk.data.model.GetPollsResponse
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.domain.Constants
import com.samadhan.sdk.domain.service.PollSevices.PoleRepository
import javax.inject.Inject

class GetPoleUseCase @Inject constructor(
    private val repository: PoleRepository
) {
    suspend operator fun invoke(): ServiceResult<GetPollsResponse> {
        return try {
            val token = Constants.token
            val userId = Constants.userId
            val result = repository.getPolls(GetPollsRequest() ,"Bearer $token")
            ServiceResult.Success(result)
        } catch (e: Exception) {
            ServiceResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}