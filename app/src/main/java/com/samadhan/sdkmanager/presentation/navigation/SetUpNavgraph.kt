package com.samadhan.sdkmanager.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.samadhan.sdkmanager.presentation.controller.LoginController

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login_screen") {
        composable(Screens.LoginController.route) {
            LoginController(navController)
        }
    }
}
