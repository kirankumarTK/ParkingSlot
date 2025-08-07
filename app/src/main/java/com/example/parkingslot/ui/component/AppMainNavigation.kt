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
import com.example.parkingslot.modules.app_flow.SharedDashBoardViewModel
import com.example.parkingslot.modules.app_flow.dashboard.Dashboard

@Composable
fun AppMainNavigation(
    innerPadding: PaddingValues,
    navController: NavHostController,
    sharedDashBoardViewModel: SharedDashBoardViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = "dashboard",
        enterTransition = { slideInHorizontally { fullWidth -> fullWidth } + fadeIn() },
        exitTransition = { slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut() },
        popEnterTransition = { slideInHorizontally { fullWidth -> -fullWidth } + fadeIn() },
        popExitTransition = { slideOutHorizontally { fullWidth -> fullWidth } + fadeOut() }) {

        composable("dashboard") {
            Dashboard(innerPadding,sharedDashBoardViewModel)
        }
    }
}