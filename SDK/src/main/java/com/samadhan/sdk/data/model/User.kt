package com.samadhan.sdk.data.model

data class User(
    val id: String,
    val name: String,
    val email: String
)

data class UserDto(
    val id: String,
    val name: String,
    val email: String
) {
    fun toDomain() = User(id, name, email)
}