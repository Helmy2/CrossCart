package org.example.cross.card.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.navigation.TopLevelRoutes
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.core.presentation.navigation.AppNavHost
import org.example.cross.card.core.presentation.navigation.mainNavigationItems
import org.example.cross.card.core.util.Connectivity
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App(startDestination: Destination = Destination.Main) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    koinInject<Navigator>(parameters = { parametersOf(navController) })
    koinInject<SnackbarManager>(parameters = { parametersOf(snackbarHostState) })

    MainScaffold(startDestination)
}

@Composable
fun MainScaffold(
    startDestination: Destination,
) {
    val snackbarManager = koinInject<SnackbarManager>()
    val navigator = koinInject<Navigator>()
    val navBackStackEntry by navigator.navController.currentBackStackEntryAsState()

    val connectivity: Connectivity = koinInject()
    val state by connectivity.statusUpdates.collectAsStateWithLifecycle(
        Connectivity.Status.Connected(
            connectionType = Connectivity.ConnectionType.Unknown
        )
    )

    var isReconnected by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state.isDisconnected) {
            snackbarManager.showSnackbar("No internet connection")
            isReconnected = true
        }

        if (isReconnected && state.isConnected) {
            snackbarManager.showSnackbar("Back online")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarManager.snackbarHostState) },
    ) {
        AnimatedContent(
            targetState = TopLevelRoutes.routes.any {
                navBackStackEntry?.destination?.hasRoute(it.route::class) == true
            }
        ) {
            if (it) {
                NavigationSuiteScaffold(
                    navigationSuiteItems = {
                        mainNavigationItems(
                            onDestinationSelected = {
                                navigator.navController.apply {
                                    navigate(it) {
                                        popUpTo(graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            navBackStackEntry = navBackStackEntry
                        )
                    },
                ) {
                    AppNavHost(
                        startDestination = startDestination,
                    )
                }
            } else {
                AppNavHost(
                    startDestination = startDestination,
                )
            }
        }
    }
}

