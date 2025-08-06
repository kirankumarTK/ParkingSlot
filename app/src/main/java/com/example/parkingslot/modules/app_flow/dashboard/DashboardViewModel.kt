package com.example.parkingslot.modules.app_flow.dashboard

import androidx.lifecycle.ViewModel
import com.example.parkingslot.utils.LoggerUtils
import com.example.parkingslot.utils.fetchParkingType
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val firestore: FirebaseFirestore, private val loggerUtils: LoggerUtils
) : ViewModel() {
    internal val parkingTypeStateFlow = MutableStateFlow<Map<String, ParkingType>>(emptyMap())

    internal fun loadParkingTypeData() {
        fetchParkingType(firestore, loggerUtils, parkingTypeStateFlow)
    }
}