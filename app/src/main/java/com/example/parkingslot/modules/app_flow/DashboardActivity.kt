package com.example.parkingslot.modules.app_flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.parkingslot.ui.component.AppMainNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold {
                AppMainNavigation(navController = navController, innerPadding = it)
            }

        }
    }
}