package com.samadhan.sdk.domain.usecase.pole

import com.samadhan.sdk.data.model.GetUserSelectedOptionsResponse
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.data.model.getUserSelectedOptions
import com.samadhan.sdk.domain.Constants
import com.samadhan.sdk.domain.service.PollSevices.PoleRepository
import javax.inject.Inject

class GetUserSelectedOptionsUseCase @Inject constructor(
    private val repository: PoleRepository
) {
    suspend operator fun invoke(id:Int): ServiceResult<List<GetUserSelectedOptionsResponse>> {
        return try {
            val token = Constants.token
            val poleId = id.toLong()
            val userId  = Constants.userId
            val result = repository.getUserSelectedOptions("Bearer $token", getUserSelectedOptions(poleId.toInt(),userId.toInt()))
            ServiceResult.Success(result)
        } catch (e: Exception) {
            ServiceResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}