package com.example.parkingslot.modules.app_flow.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingslot.modules.app_flow.SharedDashBoardViewModel
import com.example.parkingslot.utils.LoggerUtils
import com.example.parkingslot.utils.fetchParkingSlot
import com.example.parkingslot.utils.fetchParkingType
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val firestore: FirebaseFirestore, private val loggerUtils: LoggerUtils
) : ViewModel() {
    internal val parkingTypeStateFlow = MutableStateFlow<Map<String, ParkingType>>(emptyMap())
    internal val showLoadingStateFlow = MutableStateFlow<Boolean>(false)
    internal val parkingSlotStateFlow = MutableStateFlow<Map<Int, ParkingSlot>>(emptyMap())

    internal fun loadParkingTypeData(sharedDashBoardViewModel: SharedDashBoardViewModel) {
        viewModelScope.launch {
            fetchParkingType(firestore, loggerUtils, parkingTypeStateFlow, sharedDashBoardViewModel)
        }
    }

    internal fun loadSlotData(collectionType: String) {
        fetchParkingSlot(firestore, loggerUtils, parkingSlotStateFlow, collectionType, showLoadingStateFlow)
    }
}