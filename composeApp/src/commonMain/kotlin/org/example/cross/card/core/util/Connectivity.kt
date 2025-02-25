package org.example.cross.card.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.Flow
import org.example.cross.card.core.util.Connectivity.Status

@Composable
expect fun connectivityState(): State<Status>


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