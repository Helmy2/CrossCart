package org.example.cross.card.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.features.cart.presentation.cartRoute
import org.example.cross.card.features.favorite.presentation.favoriteRoute
import org.example.cross.card.features.home.presentation.homeRoute
import org.example.cross.card.features.profile.presentation.profileRoute

@Composable
fun MainNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeRoute()
        favoriteRoute()
        cartRoute()
        profileRoute()
    }
}
