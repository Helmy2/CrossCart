package org.example.cross.card.core.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.features.auth.presentation.authRoute
import org.koin.compose.koinInject

@Composable
fun AppNavHost(
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    val navigator = koinInject<Navigator>()
    NavHost(
        navController = navigator.navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Destination.Main> {
            MainScreen(modifier = Modifier.fillMaxSize())
        }

        composable<Destination.Details> {
            val details = it.toRoute<Destination.Details>()
            Box(Modifier.fillMaxSize()) {
                Text("Details: ${details.id}")
            }
        }

        navigation<Destination.Auth>(
            startDestination = Destination.Auth.Login
        ) {
            authRoute()
        }

        composable<Destination.Onboarding> {
            Box(Modifier.fillMaxSize()) {
                Text("Onboarding")
            }
        }
    }
}

