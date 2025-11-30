package com.samadhan.sdk.domain.usecase

import android.util.Log
import com.samadhan.sdk.data.model.ServiceResult
import com.samadhan.sdk.data.model.User
import com.samadhan.sdk.domain.service.UserRepository
import javax.inject.Inject


class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String): ServiceResult<User> {
        return try {
            val user = repository.getUser(id)
            Log.e("TAG", "invoke: $user", )
            ServiceResult.Success(user)
        } catch (e: Exception) {
            Log.e("TAG", "invoke: $e", )

            ServiceResult.Error(e.message?:"")
        }
    }
}