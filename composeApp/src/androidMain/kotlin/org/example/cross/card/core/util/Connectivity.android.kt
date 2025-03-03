package org.example.cross.card.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import androidx.core.content.getSystemService
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class ConnectivityImp(
    private val context: Context,
) : Connectivity {
    private var networkLost: Boolean = false

    override val statusUpdates: Flow<Connectivity.Status> = callbackFlow {
        val manager = context.getSystemService<ConnectivityManager>()
            ?: throw Exception("Could not get connectivity manager")

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val capabilities = manager.getNetworkCapabilities(network)
                trySend(status(capabilities, networkLost))
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities,
            ) {
                trySend(status(networkCapabilities, networkLost))
            }

            override fun onLost(network: Network) {
                networkLost = true
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
        return if (activeNetwork != null) {
            status(getNetworkCapabilities(activeNetwork), networkLost)
        } else {
            networkLost = true
            Connectivity.Status.Disconnected
        }
    }

    private fun status(
        capabilities: NetworkCapabilities?,
        reconnected: Boolean,
    ): Connectivity.Status {
        val isWifi = capabilities?.hasTransport(TRANSPORT_WIFI) ?: false
        val isCellular = capabilities?.hasTransport(TRANSPORT_CELLULAR) ?: false
        return Connectivity.Status.Connected(
            connectionType = when {
                isWifi -> Connectivity.ConnectionType.Wifi
                isCellular -> Connectivity.ConnectionType.Mobile
                else -> Connectivity.ConnectionType.Unknown
            },
            reconnected
        )
    }

}


