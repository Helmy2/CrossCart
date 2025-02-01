package org.example.cross.card.core.domain.snackbar

import androidx.compose.material3.SnackbarHostState

interface SnackbarManager {
    val snackbarHostState: SnackbarHostState
    suspend fun showSnackbar(value: String)
    suspend fun dismissSnackbar()
}