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


data class GetPollsResponse(
    val content: List<Poll>,
    val pageable: Pageable,
    val totalPages: Int,
    val totalElements: Int,
    val last: Boolean,
    val numberOfElements: Int,
    val size: Int,
    val number: Int,
    val sort: List<Sort>,
    val first: Boolean,
    val empty: Boolean
)

data class Poll(
    val id: Int,
    val title: String,
    val description: String,
    val pollDate: String,
    val status: String
)

data class Pageable(
    val sort: List<Sort>,
    val pageNumber: Int,
    val pageSize: Int,
    val offset: Int,
    val paged: Boolean,
    val unpaged: Boolean
)

data class Sort(
    val direction: String,
    val property: String,
    val ignoreCase: Boolean,
    val nullHandling: String,
    val descending: Boolean,
    val ascending: Boolean
)

data class GetPollsRequest(
    val role: String = "",
    val searchText: String ="" ,
    val page: Int = 0,
    val size: Int = 5,
    val sortBy: String = "pollDate",
    val orderBy: String = "desc"
)

data class getUserSelectedOptions(
val pollId:Int, val userId: Int
)

data class GetUserSelectedOptionsResponse(
    val id: Int,
    val optionText: String
)

data class SubmitPollRequest(
    val pollId: Int,
    val selectedOptionIds: List<Int>,
    val userId: Int
)
data class SubmitPollResponse(
    val status: String,
    val message: String
)

data class VerifyPoleRequest(
    val pollQRCodeText: String,
    val userId: Int
)

data class QrRequest(
    val pollId: Int,
    val userId: Int
)