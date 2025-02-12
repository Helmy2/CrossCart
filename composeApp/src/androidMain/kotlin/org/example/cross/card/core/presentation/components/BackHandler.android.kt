package org.example.cross.card.core.presentation.components

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(onBack: () -> Unit) {
    androidx.activity.compose.BackHandler(
        enabled = true, onBack = onBack
    )
}