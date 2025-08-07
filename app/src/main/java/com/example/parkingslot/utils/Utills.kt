package com.example.parkingslot.utils

internal fun String.capitalizeFirstLetter(): String =
    this.lowercase().replaceFirstChar { it.uppercase() }