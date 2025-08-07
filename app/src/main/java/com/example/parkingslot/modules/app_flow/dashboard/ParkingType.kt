package com.example.parkingslot.modules.app_flow.dashboard

internal data class ParkingType(var type: String, var icon: Int, var isSelected: Boolean = false)

internal data class ParkingSlot(var bookedBy: String, var slotAvailability: Boolean)