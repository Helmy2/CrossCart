package org.example.cross.card.core.util

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

@Composable
expect fun rememberConnectivity(): Connectivity


interface Connectivity {

    val statusUpdates: Flow<Status>

    sealed interface Status {

        val isConnected: Boolean
            get() = this is Connected

        val isDisconnected: Boolean
            get() = this is Disconnected

        data class Connected(val connectionType: ConnectionType) : Status

        data object Disconnected : Status
    }

    sealed interface ConnectionType {
        data object Wifi : ConnectionType
        data object Mobile : ConnectionType
        data object Unknown : ConnectionType
    }
}