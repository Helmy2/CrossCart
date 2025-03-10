package org.example.cross.card.core.domain.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector


data class TopLevelRoute(val name: String, val route: Destination, val icon: ImageVector)

object TopLevelRoutes {
    val routes = listOf(
        TopLevelRoute("Home", Destination.Main.Home, Icons.Default.Home),
        TopLevelRoute("Favorite", Destination.Main.Favorites, Icons.Default.Favorite),
        TopLevelRoute("Cart", Destination.Main.Cart, Icons.Default.ShoppingCart),
        TopLevelRoute("Profile", Destination.Main.Profile, Icons.Default.AccountCircle),
    )
}