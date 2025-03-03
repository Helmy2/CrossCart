package org.example.cross.card.core.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.NetworkInterface


class ConnectivityImp(
    private val delay: Long = 5000L,
) : Connectivity {
    private var networkLost: Boolean = false

    override val statusUpdates: Flow<Connectivity.Status> = flow {
        while (true) {
            emit(jvmGetNetworkStatus())
            delay(delay)
        }
    }

    private fun jvmGetNetworkStatus(): Connectivity.Status {
        val interfaces = NetworkInterface.getNetworkInterfaces()?.toList().orEmpty()
            .filter { it.isUp && !it.isLoopback }

        return when {
            interfaces.hasInterface("en0", "Wi-Fi", "wlan") ->
                Connectivity.Status.Connected(Connectivity.ConnectionType.Wifi, networkLost)

            interfaces.hasInterface(
                "en",
                "Ethernet",
                "Local Area Connection"
            ) -> Connectivity.Status.Connected(Connectivity.ConnectionType.Wifi, networkLost)

            interfaces.hasInterface(
                "rmnet",
                "pdp"
            ) -> Connectivity.Status.Connected(Connectivity.ConnectionType.Unknown, networkLost)

            else -> {
                networkLost = true
                Connectivity.Status.Disconnected
            }
        }
    }

    private fun List<NetworkInterface>.hasInterface(vararg keywords: String): Boolean {
        return any { networkInterface ->
            keywords.any { keyword ->
                networkInterface.name.contains(
                    keyword,
                    ignoreCase = true
                ) || networkInterface.displayName.contains(
                    keyword,
                    ignoreCase = true
                )
            }
        }
    }
}

