package com.example.parkingslot.utils

import com.example.parkingslot.R
import com.example.parkingslot.modules.app_flow.dashboard.ParkingType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow

internal fun fetchParkingType(
    firestore: FirebaseFirestore,
    loggerUtils: LoggerUtils,
    parkingTypeStateFlow: MutableStateFlow<Map<String, ParkingType>>
) {

    val parkingType: MutableMap<String, ParkingType> = mutableMapOf<String, ParkingType>()

    firestore.collection("common_read_collection").document("parking_type").get()
        .addOnSuccessListener {
            val typeList = it.get("type") as? List<*> ?: emptyList<Any>()

            typeList.forEach {
                if (it != null) {
                    parkingType[it.toString()] = ParkingType(
                        it.toString(),
                        icon = if (it == "CAR") R.drawable.car_logo else R.drawable.applogo
                    )
                }
            }
            parkingTypeStateFlow.value = parkingType

        }.addOnFailureListener {
            loggerUtils.error(msg = it.message.toString())
        }
}