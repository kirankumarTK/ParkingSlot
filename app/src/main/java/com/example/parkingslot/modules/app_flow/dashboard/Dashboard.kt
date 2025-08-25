package com.example.parkingslot.modules.app_flow.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.parkingslot.modules.loginFlow.LoadingOverlay
import com.example.parkingslot.R
import com.example.parkingslot.modules.app_flow.SharedDashBoardViewModel
import com.example.parkingslot.ui.component.appBackground
import com.example.parkingslot.utils.capitalizeFirstLetter

@Composable
fun Dashboard(innerPadding: PaddingValues, sharedDashBoardViewModel: SharedDashBoardViewModel) {
    val dashBoardViewModel: DashboardViewModel = hiltViewModel()
    val parkingTypeMap by dashBoardViewModel.parkingTypeStateFlow.collectAsState()
    val parkingType = parkingTypeMap.entries.toList()
    val showLoading by dashBoardViewModel.showLoadingStateFlow.collectAsState()
    var selectedIndex by remember { mutableIntStateOf(-1) }

    val parkingSlotMap by dashBoardViewModel.parkingSlotStateFlow.collectAsState()
    val parkingSlot = parkingSlotMap.entries.toList()
    val gridState = rememberLazyGridState() // used to remember scroll position

    LaunchedEffect(Unit) {
        dashBoardViewModel.loadParkingTypeData(sharedDashBoardViewModel)

        dashBoardViewModel.parkingTypeStateFlow.collect {
            if (it.isNotEmpty()) {
                selectedIndex = 0
                dashBoardViewModel.showLoadingStateFlow.value = true

                dashBoardViewModel.loadSlotData(it.entries.first().value.type.lowercase())
            }
        }

    }

    // Scroll to top when new parkingSlotMap is emitted
    LaunchedEffect(parkingSlotMap) {
        gridState.scrollToItem(0)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appBackground())
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.padding(20.dp)
        ) {
            items(parkingType.size) { index ->
                val isSelected = index == selectedIndex
                DashboardItemView(
                    parkingType[index].value, isSelected, onClick = {
                        //  need to fetch data only if different position selected

                        if (!showLoading && selectedIndex != index) {
                            selectedIndex = index
                            dashBoardViewModel.showLoadingStateFlow.value = true
                            dashBoardViewModel.loadSlotData(it)
                        }
                    })
            }
        }

        ShowAvailableParkingSlot(parkingSlot, gridState)

    }

}
/* isSelected  used to set border for current selected item */
@Composable
private fun DashboardItemView(
    parkingType: ParkingType, isSelected: Boolean, onClick: (String) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp)
            .widthIn(min = 100.dp, max = 100.dp)
            .heightIn(min = 80.dp, max = 80.dp)
            .fillMaxSize()
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp, brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary
                            )
                        ), shape = RoundedCornerShape(15.dp)
                    )
                } else {
                    Modifier
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .clickable {
                    onClick(parkingType.type.lowercase())
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(parkingType.icon),
                contentDescription = "",
                Modifier.size(40.dp)
            )

            Text(
                text = parkingType.type.capitalizeFirstLetter(),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 5.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ShowAvailableParkingSlot(
    list: List<Map.Entry<Int, ParkingSlot>>,
    gridState: LazyGridState
) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
    ) {

        if (false) LoadingOverlay(true)
        else LazyVerticalGrid(
            state = gridState,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.1f))
                .padding(10.dp),
            columns = GridCells.Adaptive(120.dp),
        ) {
            items(list.size) { index ->
                ParkingSlotView(list[index].value, list[index].key)

            }
        }
    }
}


@Composable
private fun ParkingSlotView(parkingSlot: ParkingSlot, slotNo: Int) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(10.dp)
            .widthIn(min = 80.dp, max = 80.dp)
            .heightIn(min = 100.dp, max = 100.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
        ) {
            Image(
                painter = painterResource(
                    if (parkingSlot.slotAvailability) R.drawable.parking_available
                    else R.drawable.parking_not
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .padding(top = 5.dp),
            )
            Text(
                "Slot No $slotNo",
                modifier = Modifier.padding(top = 10.dp),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}