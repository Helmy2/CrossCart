package org.example.cross.card.domain.util

import kotlinx.coroutines.CoroutineDispatcher

expect object PlatformDispatchers {
    val Io: CoroutineDispatcher
    val Main: CoroutineDispatcher
    val Default: CoroutineDispatcher
}