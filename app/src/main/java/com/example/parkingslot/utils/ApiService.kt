package com.example.parkingslot.utils

import com.example.parkingslot.R
import com.example.parkingslot.modules.app_flow.SharedDashBoardViewModel
import com.example.parkingslot.modules.app_flow.dashboard.ParkingSlot
import com.example.parkingslot.modules.app_flow.dashboard.ParkingType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow

internal fun fetchParkingType(
    firestore: FirebaseFirestore,
    loggerUtils: LoggerUtils,
    parkingTypeStateFlow: MutableStateFlow<Map<String, ParkingType>>,
    sharedDashBoardViewModel: SharedDashBoardViewModel
) {
    sharedDashBoardViewModel.showPageLoadingStateFlow.value = true
    val parkingType: MutableMap<String, ParkingType> = mutableMapOf<String, ParkingType>()
    firestore.collection("common_read_collection").document("parking_type").get()
        .addOnSuccessListener {
            val typeList = it.get("type") as? List<*> ?: emptyList<Any>()
            typeList.forEach {
                if (it != null) {
                    parkingType[it.toString()] = ParkingType(
                        it.toString(),
                        icon = if (it == "CAR") R.drawable.car_logo else if (it == "BIKE") R.drawable.bike_logo else if (it == "CYCLE") R.drawable.cycle_logo else R.drawable.guests_logo
                    )
                }
            }
            parkingTypeStateFlow.value = parkingType
            sharedDashBoardViewModel.showPageLoadingStateFlow.value = false

        }.addOnFailureListener {
            loggerUtils.error(msg = it.message.toString())
            sharedDashBoardViewModel.showPageLoadingStateFlow.value = false
        }
}

internal fun fetchParkingSlot(
    firestore: FirebaseFirestore,
    loggerUtils: LoggerUtils,
    parkingSlotStateFlow: MutableStateFlow<Map<Int, ParkingSlot>>,
    collectionType: String,
    showLoadingStateFlow: MutableStateFlow<Boolean>
) {
    val parkingSlot: MutableMap<Int, ParkingSlot> = mutableMapOf<Int, ParkingSlot>()

    firestore.collection("common_read_collection").document("slot").collection(collectionType).get()
        .addOnSuccessListener {
            for (document in it) {
                parkingSlot[document.id.toInt()] = ParkingSlot(
                    document.data["booked_by"].toString(),
                    document.data["slot_availability"] as Boolean
                )
            }
            parkingSlotStateFlow.value = parkingSlot
            showLoadingStateFlow.value = false
        }.addOnFailureListener {
            loggerUtils.info(msg = it.message.toString())
            showLoadingStateFlow.value = false
        }
}