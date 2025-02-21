package org.example.cross.card.core.domain.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    val name: String get() = this::class.simpleName ?: "unknown"

    @Serializable
    data object Onboarding : Destination()

    @Serializable
    data object Auth : Destination() {
        @Serializable
        data object Login : Destination()

        @Serializable
        data object Register : Destination()

        @Serializable
        data object RestPassword : Destination()
    }

    @Serializable
    data object Main : Destination() {
        @Serializable
        data object Home : Destination()

        @Serializable
        data object Favorites : Destination()

        @Serializable
        data object Profile : Destination()

        @Serializable
        data object Cart : Destination()
    }

    @Serializable
    data object Checkout : Destination()
}