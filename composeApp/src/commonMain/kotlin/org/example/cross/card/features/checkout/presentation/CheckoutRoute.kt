package org.example.cross.card.features.checkout.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.example.cross.card.core.domain.navigation.Destination
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.checkoutRoute() {
    composable<Destination.Checkout> {
        val viewModel: CheckoutViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        CheckoutScreen(
            state = state,
            onEvent = viewModel::handleEvent,
            modifier = Modifier.fillMaxSize().systemBarsPadding()
        )
    }
}