package com.example.parkingslot.modules.loginFlow

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.parkingslot.R
import com.example.parkingslot.modules.app_flow.DashboardActivity
import com.example.parkingslot.ui.component.AppNavigation
import com.example.parkingslot.ui.component.AppToolBar
import com.example.parkingslot.ui.theme.ParkingSlotTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.get

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
                val loading = sharedLoginViewModel.isLoadingStateFlow.collectAsState()

                // redirecting to dashboard
                sharedLoginViewModel.moveToDashboardLiveData.observe(this) { it ->
                    if (it) {
                        startActivity(Intent(this, DashboardActivity::class.java))
                        finish()
                        sharedLoginViewModel.moveToDashboardLiveData.value = false
                    }
                }

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
                    LoadingOverlay(loading.value)
                }
            }
        }
    }

    private fun showTitle(title: String): Boolean {
        return (title == "registration")
    }

    private fun showNavigation(title: String): Boolean {
        return (title == "registration")
    }
}

@Preview
@Composable
internal fun LoadingOverlay(isLoading: Boolean = true) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable(enabled = false) {} // consume touches
            .zIndex(1f), contentAlignment = Alignment.Center) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(color = Color.White)
//                Text("Processing", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}