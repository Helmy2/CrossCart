package org.example.cross.card.features.profile.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.example.cross.card.core.domain.navigation.Destination
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.profileRoute() {
    composable<Destination.Main.Profile> {
        val viewModel: ProfileViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        ProfileScreen(
            state = state,
            onEvent = viewModel::handleEvent,
            modifier = Modifier.fillMaxSize().systemBarsPadding()
        )
    }
}