package com.example.parkingslot.ui.component

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parkingslot.modules.loginFlow.ValidateViewModel
import com.example.parkingslot.modules.loginFlow.login.Login
import com.example.parkingslot.modules.loginFlow.registration.UserRegistration

@Composable
fun AppNavigation(
    innerPadding: PaddingValues,
    navController: NavHostController,
    sharedLoginViewModel: ValidateViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "Login",
        enterTransition = { slideInHorizontally { fullWidth -> fullWidth } + fadeIn() },
        exitTransition = { slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut() },
        popEnterTransition = { slideInHorizontally { fullWidth -> -fullWidth } + fadeIn() },
        popExitTransition = { slideOutHorizontally { fullWidth -> fullWidth } + fadeOut() }) {
        composable(route = "login") {
            Login(navController, sharedLoginViewModel)

        }
        composable("registration") {
            UserRegistration(navController, sharedLoginViewModel, innerPadding)
        }

    }
}

