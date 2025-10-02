package com.samadhan.sdk.data.model

data class LoginRequest(
    val email: String,
    val password: String,
    val remember: Boolean = false
)

data class UserDetails(
    val userId: Int,
    val userName: String,
    val email: String,
    val userRole: String
)

data class LoginResponse(
    val status: String,
    val message: String,
    val jwtToken: String,
    val userDetails: UserDetails
)