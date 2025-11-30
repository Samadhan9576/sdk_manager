package com.samadhan.sdk.domain.usecase.pole

import android.util.Log
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.data.model.SubmitPollRequest
import com.samadhan.sdk.data.model.SubmitPollResponse
import com.samadhan.sdk.data.model.VerifyPoleRequest
import com.samadhan.sdk.data.model.getUserSelectedOptions
import com.samadhan.sdk.domain.Constants
import com.samadhan.sdk.domain.service.PollSevices.PoleRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class SavePoleUseCase @Inject constructor(
    private val repository: PoleRepository
) {
    suspend operator fun invoke(id: Int, optionId: Int): ServiceResult<SubmitPollResponse> {
        return try {
            val token = Constants.token
            val poleId = id.toLong()
            val userId = Constants.userId
            val result = repository.savePole(
                "Bearer $token",
                SubmitPollRequest(
                    poleId.toInt(),
                    selectedOptionIds = listOf(optionId),
                    userId.toInt()
                )
            )
            ServiceResult.Success(result)
        } catch (e: Exception) {
            ServiceResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun removePole(id: Int): ServiceResult<ResponseBody> {
        return try {
            val token = Constants.token
            val poleId = id.toLong()
            val userId  = Constants.userId
            val result = repository.removePole("Bearer $token", getUserSelectedOptions(poleId.toInt(),userId.toInt()))
            ServiceResult.Success(result)
        } catch (e: Exception) {
            Log.e("TAG", "removePole: ${e.message}", )
            ServiceResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }
    suspend fun scanPole(QR:String): ServiceResult<ResponseBody> {
        return try {
            val token = Constants.token
            val userId  = Constants.userId
            val result = repository.scanPole("Bearer $token", VerifyPoleRequest(QR,userId.toInt()))
            ServiceResult.Success(result)
        } catch (e: Exception) {
            Log.e("TAG", "removePole: ${e.message}", )
            ServiceResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}