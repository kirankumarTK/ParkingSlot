package com.example.parkingslot.modules.app_flow.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun Dashboard() {
    val dashBoardViewModel: DashboardViewModel = hiltViewModel()

    val parkingTypeMap by dashBoardViewModel.parkingTypeStateFlow.collectAsState()
    val parkingType = parkingTypeMap.entries.toList()

    dashBoardViewModel.loadParkingTypeData()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(parkingType.size) { index ->
            DashboardItemView(parkingType[index].value)
        }
    }
}

@Composable
fun DashboardItemView(parkingType: ParkingType) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(15.dp)
            .clickable {

            }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(parkingType.icon),
                contentDescription = "",
                Modifier
                    .size(50.dp)
                    .padding(5.dp),
                tint = MaterialTheme.colorScheme.tertiary,
            )

            Text(
                text = "${parkingType.type} Parking".uppercase(),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }
}