package com.samadhan.sdk.domain.usecase.pole

import android.util.Log
import com.samadhan.sdk.data.model.LoginRequest
import com.samadhan.sdk.data.model.LoginResponse
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.domain.Constants
import com.samadhan.sdk.domain.service.PollSevices.PoleRepository
import javax.inject.Inject

class PoleLoginUseCase @Inject constructor(
    private val repository: PoleRepository
) {
    suspend operator fun invoke(loginRequest: LoginRequest): ServiceResult<LoginResponse> {
        return try {
            ServiceResult.Loading
            val response = repository.poleLogin(loginRequest)
            if (response.status == "success") {
                Constants.token = response.jwtToken
                Constants.userId = response.userDetails.userId
                ServiceResult.Success(response)
            } else {
                ServiceResult.Error("serviceError")
            }

        } catch (e: Exception) {
            Log.e("TAG", "invoke: $e")
            ServiceResult.Error(e.message ?: "")
        }
    }
}