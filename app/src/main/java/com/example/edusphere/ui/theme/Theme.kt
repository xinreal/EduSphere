package com.example.edusphere.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = SurfaceSoft,
    primaryContainer = AccentMint,
    secondary = PrimaryBlueDark,
    background = Background,
    surface = SurfaceSoft,
    onSurface = TextPrimary,
    onBackground = TextPrimary
)

private val DarkColors = darkColorScheme(
    primary = AccentMint,
    secondary = PrimaryBlue,
    background = TextPrimary,
    surface = PrimaryBlueDark
)

@Composable
fun EduSphereTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
