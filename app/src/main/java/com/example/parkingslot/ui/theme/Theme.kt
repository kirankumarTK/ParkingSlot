package com.example.parkingslot.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Dark_Primary,
    secondary = Dark_Secondary,
    tertiary = Dark_Tertiary,
    error = Dark_Error,
    onPrimary = Dark_OnPrimary,
    onSecondary = Dark_OnSecondary,
    onTertiary = Dark_OnTertiary,
    onError = Dark_OnError,
    primaryContainer = Dark_PrimaryContainer,
    secondaryContainer = Dark_SecondaryContainer,
    tertiaryContainer = Dark_TertiaryContainer,
    errorContainer = Dark_ErrorContainer,
    onPrimaryContainer = Dark_OnPrimaryContainer,
    onSecondaryContainer = Dark_OnSecondaryContainer,
    onTertiaryContainer = Dark_OnTertiaryContainer,
    onErrorContainer = Dark_OnErrorContainer,
    surfaceDim = Dark_SurfaceDim,
    surface = Dark_Surface,
    surfaceBright = Dark_SurfaceBright,
    inverseSurface = Dark_InverseSurface,
    surfaceContainerLowest = Dark_SurfaceContainerLowest,
    surfaceContainerLow = Dark_SurfaceContainerLow,
    surfaceContainer = Dark_SurfaceContainer,
    surfaceContainerHigh = Dark_SurfaceContainerHigh,
    surfaceContainerHighest = Dark_SurfaceContainerHighest,
    inverseOnSurface = Dark_InverseOnSurface,
    inversePrimary = Dark_InverseOnPrimary,
    onSurface = Dark_OnSurface,
    onSurfaceVariant = Dark_OnSurfaceVar,
    outline = Dark_Outline,
    outlineVariant = Dark_OutlineVariant,
    scrim = Dark_Scrim,
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    error = Error,
    onPrimary = OnPrimary,
    onSecondary = OnSecondary,
    onTertiary = OnTertiary,
    onError = OnError,
    primaryContainer = PrimaryContainer,
    secondaryContainer = SecondaryContainer,
    tertiaryContainer = TertiaryContainer,
    errorContainer = ErrorContainer,
    onPrimaryContainer = OnPrimaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    onErrorContainer = OnErrorContainer,
    surfaceDim = SurfaceDim,
    surface = Surface,
    surfaceBright = SurfaceBright,
    inverseSurface = InverseSurface,
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainer,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerHighest = SurfaceContainerHighest,
    inverseOnSurface = InverseOnSurface,
    inversePrimary = InverseOnPrimary,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVar,
    outline = Outline,
    outlineVariant = OutlineVariant,
    scrim = Scrim,


)

@Composable
fun ParkingSlotTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}