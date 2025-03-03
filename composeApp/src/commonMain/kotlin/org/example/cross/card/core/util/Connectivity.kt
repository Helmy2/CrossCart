package org.example.cross.card.core.util

import kotlinx.coroutines.flow.Flow


interface Connectivity {

    val statusUpdates: Flow<Status>

    sealed interface Status {

        val isConnected: Boolean
            get() = this is Connected

        val isReconnecting: Boolean
            get() = (this as? Connected)?.reconnecting ?: false

        val isDisconnected: Boolean
            get() = this is Disconnected

        data class Connected(
            val connectionType: ConnectionType,
            val reconnecting: Boolean = false
        ) :
            Status

        data object Disconnected : Status
    }

    sealed interface ConnectionType {
        data object Wifi : ConnectionType
        data object Mobile : ConnectionType
        data object Unknown : ConnectionType
    }
}