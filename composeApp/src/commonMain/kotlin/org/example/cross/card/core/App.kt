package org.example.cross.card.core

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.core.domain.usecase.IsUserLongedInUseCase
import org.example.cross.card.core.presentation.CrossCartTheme
import org.example.cross.card.core.presentation.navigation.AppNavHost
import org.example.cross.card.di.appModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(appModule)
            printLogger()
        },
    ) {
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }
        koinInject<Navigator>(parameters = { parametersOf(navController) })
        koinInject<SnackbarManager>(parameters = { parametersOf(snackbarHostState) })

        val isUserLongedInUseCase = koinInject<IsUserLongedInUseCase>()

        var startDestination by remember { mutableStateOf<Destination>(Destination.Main) }

        LaunchedEffect(Unit) {
            startDestination = if (isUserLongedInUseCase()) Destination.Main else Destination.Auth
        }

        CrossCartTheme {
            MainScaffold(startDestination)
        }
    }
}

@Composable
fun MainScaffold(
    startDestination: Destination,
) {
    val snackbarManager = koinInject<SnackbarManager>()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarManager.snackbarHostState) },
    ) {
        AppNavHost(
            startDestination = startDestination,
        )
    }
}