package com.example.parkingslot.modules.app_flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.parkingslot.LoadingOverlay
import com.example.parkingslot.ui.component.AppMainNavigation
import com.example.parkingslot.ui.theme.ParkingSlotTheme
import com.example.parkingslot.utils.capitalizeFirstLetter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkingSlotTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val sharedDashBoardViewModel: SharedDashBoardViewModel = hiltViewModel()
                val showLoading by sharedDashBoardViewModel.showPageLoadingStateFlow.collectAsState()

                ModalNavigationDrawer(
                    drawerState = drawerState, drawerContent = {
                        androidx.compose.material3.ModalDrawerSheet {
                            Text(
                                "Menu",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Divider()
                            NavigationDrawerItem(
                                label = { Text("Home") },
                                selected = false,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                })
                            NavigationDrawerItem(
                                label = { Text("Settings") },
                                selected = false,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                })
                        }
                    }) {
                    Scaffold(
                        floatingActionButton = {},
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.onPrimaryContainer),
                        topBar = {

                            TopAppBar(
                                title = {
                                Text(
                                    currentRoute.toString().take(50).capitalizeFirstLetter()
                                )
                            }, navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch { drawerState.open() }
                                }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }, colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,   // Background color
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer, // Title text color
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer, // Icon color
                                actionIconContentColor = MaterialTheme.colorScheme.primaryContainer
                            )
                            )

                        }) { innerPadding ->

                        AppMainNavigation(innerPadding, navController,sharedDashBoardViewModel)
                        LoadingOverlay(showLoading)
                    }

                }
            }
        }
    }
}


