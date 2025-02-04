package org.example.cross.card.product.presentation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.product.presentation.home.HomeScreen
import org.example.cross.card.product.presentation.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.productRoute() {
    composable<Destination.Main.Products.Home> {
        val viewModel: HomeViewModel = koinViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle()
        HomeScreen(
            state = state.value, onEvent = viewModel::handleEvent
        )
    }
}
