package com.samadhan.sdk.SDK.data

import retrofit2.http.GET
import retrofit2.http.Path

data class User(
    val id: String,
    val name: String,
    val email: String
)

interface UserApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): UserDto
}

data class UserDto(
    val id: String,
    val name: String,
    val email: String
) {
    fun toDomain() = User(id, name, email)
}

interface UserRepository {
    suspend fun getUser(id: String): User
}

class UserRepositoryImpl(
    private val api: UserApiService
) : UserRepository {
    override suspend fun getUser(id: String): User {
        return api.getUser(id).toDomain()
    }
}

class GetUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String): User {
        return repository.getUser(id)
    }
}