package com.samadhan.sdkmanager.domain.event

sealed class LoginEvent {
    object OnLoginSuccess : LoginEvent()
    object ShowSnackBar : LoginEvent()
    class PoleDetailSuccess(var clickId:Int,var id:Int) : LoginEvent()
    object isLoading : LoginEvent()
}
