package org.example.cross.card.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import platform.Network.nw_interface_type_wifi
import platform.Network.nw_path_get_status
import platform.Network.nw_path_is_expensive
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfied
import platform.Network.nw_path_uses_interface_type
import platform.darwin.DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL
import platform.darwin.dispatch_queue_create


@Composable
actual fun connectivityState(): State<Connectivity.Status> {
    return ConnectivityImp().statusUpdates.collectAsStateWithLifecycle(
        Connectivity.Status.Connected(
            connectionType = Connectivity.ConnectionType.Unknown
        )
    )
}

class ConnectivityImp : Connectivity {
    private var networkLost: Boolean = false

    override val statusUpdates: Flow<Connectivity.Status> = callbackFlow {
        val monitor = nw_path_monitor_create()
        val queue = dispatch_queue_create(
            label = "org.example.cross.card",
            attr = DISPATCH_QUEUE_SERIAL_WITH_AUTORELEASE_POOL,
        )

        nw_path_monitor_set_update_handler(monitor) { path ->
            val status = nw_path_get_status(path)
            when {
                status == nw_path_status_satisfied -> {
                    val isWifi = nw_path_uses_interface_type(path, nw_interface_type_wifi)
                    val isExpensive = nw_path_is_expensive(path)

                    trySend(
                        Connectivity.Status.Connected(
                            connectionType = when {
                                isWifi -> Connectivity.ConnectionType.Wifi
                                isExpensive -> Connectivity.ConnectionType.Mobile
                                else -> Connectivity.ConnectionType.Unknown
                            },
                            networkLost
                        ),
                    )
                }

                else -> {
                    networkLost = true
                    trySend(Connectivity.Status.Disconnected)
                }
            }
        }

        nw_path_monitor_set_queue(monitor, queue)
        nw_path_monitor_start(monitor)

        awaitClose {
            nw_path_monitor_cancel(monitor)
        }
    }
}