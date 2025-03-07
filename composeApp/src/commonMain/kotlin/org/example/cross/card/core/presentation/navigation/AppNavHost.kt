package org.example.cross.card.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.features.auth.presentation.authRoute
import org.example.cross.card.features.cart.presentation.cartRoute
import org.example.cross.card.features.favorite.presentation.favoriteRoute
import org.example.cross.card.features.home.presentation.homeRoute
import org.example.cross.card.features.onboarding.onboardingRoute
import org.example.cross.card.features.profile.presentation.profileRoute
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
        navigation<Destination.Main>(
            startDestination = Destination.Main.Home
        ) {
            homeRoute()
            favoriteRoute()
            cartRoute()
            profileRoute()
        }

        navigation<Destination.Auth>(
            startDestination = Destination.Auth.Login
        ) {
            authRoute()
        }

        onboardingRoute(
            onCompleted = {
                navigator.navigateAsStart(Destination.Auth)
            }
        )
    }
}

