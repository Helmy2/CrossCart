package org.example.cross.card.core.presentation

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun SystemAppearance(isDark: Boolean) {

}

@Composable
actual fun getDynamicColorScheme(
    darkTheme: Boolean,
    darkColorScheme: ColorScheme,
    lightColorScheme: ColorScheme
): ColorScheme {
    return if (darkTheme) darkColorScheme else lightColorScheme
}