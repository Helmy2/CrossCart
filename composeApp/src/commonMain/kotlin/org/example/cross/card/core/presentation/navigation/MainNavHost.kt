package org.example.cross.card.core.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.cross.card.core.domain.navigation.Destination

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
        composable<Destination.Main.Home> {
            Box(Modifier.fillMaxSize()) {
                Text("Home")
            }
        }
        composable<Destination.Main.Profile> {
            Box(Modifier.fillMaxSize()) {
                Text("Profile")
            }
        }
    }
}
