package org.example.cross.card.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@Composable
actual fun rememberConnectivity(): Connectivity {
    val context = LocalContext.current
    return remember<Connectivity> {
        ConnectivityImp(context)
    }
}

class ConnectivityImp(
    private val context: Context,
) : Connectivity {
    override val statusUpdates: Flow<Connectivity.Status> = callbackFlow {
        val manager = context.getSystemService<ConnectivityManager>()
            ?: throw Exception("Could not get connectivity manager")

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val capabilities = manager.getNetworkCapabilities(network)
                trySend(status(capabilities))
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities,
            ) {
                trySend(status(networkCapabilities))
            }

            override fun onLost(network: Network) {
                trySend(Connectivity.Status.Disconnected)
            }
        }

        try {
            manager.registerDefaultNetworkCallback(networkCallback)

            val initialStatus = manager.initialStatus()
            trySend(initialStatus)

            awaitCancellation()
        } finally {
            manager.unregisterNetworkCallback(networkCallback)
        }
    }

    private fun ConnectivityManager.initialStatus(): Connectivity.Status {
        return activeNetwork?.let { network ->
            getNetworkCapabilities(network)?.let { capabilities ->
                status(capabilities)
            }
        } ?: Connectivity.Status.Disconnected
    }
}

private fun status(
    capabilities: NetworkCapabilities?,
): Connectivity.Status.Connected {
    val isWifi = capabilities?.hasTransport(TRANSPORT_WIFI) ?: false
    val isCellular = capabilities?.hasTransport(TRANSPORT_CELLULAR) ?: false
    return Connectivity.Status.Connected(
        connectionType = when {
            isWifi -> Connectivity.ConnectionType.Wifi
            isCellular -> Connectivity.ConnectionType.Mobile
            else -> Connectivity.ConnectionType.Unknown
        }
    )
}

