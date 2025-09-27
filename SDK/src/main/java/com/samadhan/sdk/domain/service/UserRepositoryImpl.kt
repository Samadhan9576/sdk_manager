package com.samadhan.sdk.domain.service

import com.samadhan.sdk.data.model.User

class UserRepositoryImpl(
    private val api: UserApiService
) : UserRepository {
    override suspend fun getUser(id: String): User {
        return api.getUser(id).toDomain()
    }
}