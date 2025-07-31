package com.example.parkingslot.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush


@Composable
internal fun AppBackground(): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.tertiaryContainer
        )
    )
}