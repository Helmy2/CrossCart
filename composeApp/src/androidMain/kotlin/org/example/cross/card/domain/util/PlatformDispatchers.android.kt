package org.example.cross.card.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual object PlatformDispatchers {
    actual val Io: CoroutineDispatcher = Dispatchers.IO
    actual val Main: CoroutineDispatcher = Dispatchers.Main
    actual val Default: CoroutineDispatcher = Dispatchers.Default
}