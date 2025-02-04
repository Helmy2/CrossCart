package org.example.cross.card.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.ic_dark_mode
import crosscart.composeapp.generated.resources.ic_light_mode
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.core.domain.usecase.IsUserLongedInUseCase
import org.example.cross.card.core.presentation.CrossCartTheme
import org.example.cross.card.core.presentation.local.LocalThemeIsDark
import org.example.cross.card.core.presentation.navigation.AppNavHost
import org.example.cross.card.di.appModule
import org.jetbrains.compose.resources.vectorResource
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
            Box(modifier = Modifier.systemBarsPadding().fillMaxSize()) {
                MainScaffold(startDestination)
                ThemeSwitch(modifier = Modifier.padding(end = 16.dp).align(Alignment.TopEnd))
            }
        }
    }
}

@Composable
fun ThemeSwitch(modifier: Modifier = Modifier) {
    var isDark by LocalThemeIsDark.current
    IconButton(
        onClick = {
            isDark = !isDark
        },
        content = {
            Icon(
                vectorResource(
                    if (isDark) Res.drawable.ic_light_mode
                    else Res.drawable.ic_dark_mode
                ), contentDescription = null
            )
        },
        modifier = modifier,
    )
}

@Composable
fun MainScaffold(
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    val snackbarManager = koinInject<SnackbarManager>()
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarManager.snackbarHostState) },
    ) {
        AppNavHost(
            startDestination = startDestination,
        )
    }
}