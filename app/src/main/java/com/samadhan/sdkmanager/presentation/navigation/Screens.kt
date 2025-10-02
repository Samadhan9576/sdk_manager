package com.samadhan.sdkmanager.presentation.navigation

sealed class Screens(val route:String) {
    object LoginController : Screens("login_screen")
}