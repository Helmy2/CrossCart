package org.example.cross.card.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.InetAddress
import java.net.UnknownHostException

@Composable
actual fun connectivityState(): State<Connectivity.Status> {
    return ConnectivityImp().statusUpdates.collectAsStateWithLifecycle(
        Connectivity.Status.Connected(
            connectionType = Connectivity.ConnectionType.Unknown
        )
    )
}

class ConnectivityImp(
    private val delay: Long = 5000L,
    private val websiteDomain: String = "www.google.com"
) : Connectivity {
    override val statusUpdates: Flow<Connectivity.Status> = flow {
        while (true) {
            val isConnected = isInternetAvailable()
            emit(
                if (isConnected) Connectivity.Status.Connected(
                    connectionType = Connectivity.ConnectionType.Unknown
                ) else Connectivity.Status.Disconnected
            )
            delay(delay)
        }
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            val address = InetAddress.getByName(websiteDomain)
            address != null
        } catch (e: UnknownHostException) {
            false
        }
    }
}

