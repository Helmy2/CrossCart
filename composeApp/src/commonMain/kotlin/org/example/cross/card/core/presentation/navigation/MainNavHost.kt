package org.example.cross.card.core.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.product.presentation.productRoute

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
        navigation<Destination.Main.Products>(
            startDestination = Destination.Main.Products.Home
        ) {
            productRoute()
        }
        composable<Destination.Main.Profile> {
            Box(Modifier.fillMaxSize()) {
                Text("Profile")
            }
        }
    }
}
