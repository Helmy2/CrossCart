package org.example.cross.card.core.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.navigation.TopLevelRoutes

fun NavigationSuiteScope.mainNavigationItems(
    onDestinationSelected: (destinations: Destination) -> Unit,
    navBackStackEntry: NavBackStackEntry?
) {
    TopLevelRoutes.routes.forEach { topLevelRoute ->
        val isSelected =
            navBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
        item(
            icon = {
                Icon(
                    topLevelRoute.icon,
                    contentDescription = topLevelRoute.name,
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            },
            label = {
                Text(
                    text = topLevelRoute.name,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            },
            selected = isSelected,
            onClick = {
                onDestinationSelected(topLevelRoute.route)
            }
        )
    }
}
