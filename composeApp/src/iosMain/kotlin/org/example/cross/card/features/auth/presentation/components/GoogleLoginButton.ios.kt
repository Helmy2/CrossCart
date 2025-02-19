package org.example.cross.card.features.auth.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun GoogleLoginButton(
    loading: Boolean,
    onAuthResult: (result: Result<Unit>) -> Unit,
    modifier: Modifier
) {
}