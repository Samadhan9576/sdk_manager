package com.samadhan.sdkmanager.domain.event

sealed class LoginEvent {
    object OnLoginSuccess : LoginEvent()
    object ShowSnackBar : LoginEvent()
}