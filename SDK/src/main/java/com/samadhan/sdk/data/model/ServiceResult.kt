package com.samadhan.sdk.data.model

sealed class ServiceResult<out T> {
    object Loading : ServiceResult<Nothing>()
    data class Success<out T>(val data: T) : ServiceResult<T>()
    data class Error(val message:String) : ServiceResult<Nothing>()
}