package com.samadhan.sdk.domain.usecase.pole

import android.util.Log
import com.samadhan.sdk.data.model.PoleDetails.PollDetailsResponse
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.domain.Constants
import com.samadhan.sdk.domain.service.PollSevices.PoleRepository
import javax.inject.Inject

class GetPoleDetailsUseCase @Inject constructor(
    private val repository: PoleRepository
) {
    suspend operator fun invoke(id:Int): ServiceResult<PollDetailsResponse> {
        return try {
            Log.e("TAG", "GetPoleDetailsUseCase: ", )
            val token = Constants.token
            val poleId = id.toLong()
            val result = repository.getPollDetails( "Bearer $token", poleId)
            ServiceResult.Success(result)
        } catch (e: Exception) {
            Log.e("TAG", "GetPoleDetailsUseCase: ${e.message}", )

            ServiceResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}