package org.example.cross.card.core.presentation.navigation

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.example.cross.card.core.domain.navigation.Destination

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationSuiteScaffold(
        modifier = modifier,
        navigationSuiteItems = {
            val currentDestination = navBackStackEntry?.destination?.route
            val nameForCurrentDestination = currentDestination?.substringAfterLast(".")
            mainNavigationItems(
                onDestinationSelected = {
                    navController.apply {
                        navigate(it) {
                            popUpTo(graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }, currentRoute = nameForCurrentDestination ?: ""
            )
        },
    ) {
        MainNavHost(
            navController = navController,
            startDestination = Destination.Main.Home,
        )
    }
}