package org.example.cross.card.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.NetworkInterface

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
) : Connectivity {
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
                Connectivity.Status.Connected(Connectivity.ConnectionType.Wifi)

            interfaces.hasInterface(
                "en",
                "Ethernet",
                "Local Area Connection"
            ) -> Connectivity.Status.Connected(Connectivity.ConnectionType.Wifi)

            interfaces.hasInterface(
                "rmnet",
                "pdp"
            ) -> Connectivity.Status.Connected(Connectivity.ConnectionType.Unknown)

            else -> Connectivity.Status.Disconnected
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

