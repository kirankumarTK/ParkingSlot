package com.example.parkingslot.modules.loginFlow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.parkingslot.R
import com.example.parkingslot.ui.component.AppNavigation
import com.example.parkingslot.ui.component.AppToolBar
import com.example.parkingslot.ui.theme.ParkingSlotTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginMainActivity : ComponentActivity() {

    // Route to title map
    val routeTitleMap = mapOf(
        "login" to "Login",
        "registration" to "Create account",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkingSlotTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val title = routeTitleMap[currentRoute] ?: stringResource(R.string.app_name)
                val sharedLoginViewModel: ValidateViewModel = hiltViewModel()
                Scaffold(
                    floatingActionButton = {},
                    modifier = Modifier.Companion.fillMaxSize(),
                    topBar = {
                        AnimatedVisibility(
                            visible = showTitle(currentRoute ?: ""), enter = slideInHorizontally(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    delayMillis = 200 // delay before animation starts
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 300, delayMillis = 200
                                )
                            )
                        ) {
                            AppToolBar(
                                title = title,
                                showTitle = showTitle(currentRoute.toString()),
                                showBackNavigation = showNavigation(currentRoute.toString()),
                                navController = navController,
                                currentRoute = currentRoute
                            )
                        }
                    }) { innerPadding ->

                    AppNavigation(innerPadding, navController, sharedLoginViewModel)
                }
            }
        }
    }

    private fun showTitle(title: String): Boolean {
        return (title == "registration" )
    }

    private fun showNavigation(title: String): Boolean {
        return (title == "registration")
    }
}