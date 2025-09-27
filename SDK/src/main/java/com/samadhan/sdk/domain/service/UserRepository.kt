package com.samadhan.sdk.domain.service

import com.samadhan.sdk.data.model.User

interface UserRepository {
    suspend fun getUser(id: String): User
}