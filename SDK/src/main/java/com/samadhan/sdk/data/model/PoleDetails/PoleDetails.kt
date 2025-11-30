package com.samadhan.sdk.data.model.PoleDetails

data class PollDetailsResponse(
    val status: String?,
    val message: String?,
    val poll: Poll?
)

data class Poll(
    val id: Int?,
    val title: String?,
    val description: String?,
    val startDate: String?,
    val endDate: String?,
    val isMultipleVotesAllowed: Boolean?,
    val pollStatus: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val pollDate: String?,
    val pollMealType: PollMealType?,
    val options: List<Option>?,
    val createdBy: CreatedBy?,
    val updatedBy: Any?,
    val isNotificationSent: Boolean?,
    val pollQRCodeText: String?,
    val pollQRCodeUseStatus: Boolean?,
    val deletedAt: Any?,
    val isDeleted: Boolean?,
    val deletedBy: Any?
)

data class Option(
    val id: Int?,
    val optionText: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val createdBy: CreatedBy?,
    val updatedBy: Any?
)

data class CreatedBy(
    val id: Int?,
    val userName: String?,
    val firstName: String?,
    val lastName: String?,
    val phone: String?,
    val email: String?,
    val password_hash: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val createdBy: CreatedBy?,
    val updatedBy: Any?,
    val active: Boolean?,
    val isDeleted: Boolean?,
    val deletedBy: Any?,
    val deletedOn: Any?
)

data class PollMealType(
    val id: Int,
    val mealType: String
)

data class PollOption(
    val id: Int,
    val optionText: String,
    val createdAt: String,
    val updatedAt: String?,
    val createdBy: User,
    val updatedBy: User?
)

data class User(
    val id: Int,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val phone: String?,
    val email: String?,
    val password_hash: String?,
    val createdAt: String,
    val updatedAt: String?,
    val createdBy: String?,
    val updatedBy: String?,
    val active: Boolean,
    val isDeleted: Boolean,
    val deletedBy: String?,
    val deletedOn: String?
)
