package org.example.cross.card.auth.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.example.cross.card.auth.presentation.login.LoginScreen
import org.example.cross.card.auth.presentation.login.LoginViewModel
import org.example.cross.card.auth.presentation.profile.ProfileScreen
import org.example.cross.card.auth.presentation.profile.ProfileViewModel
import org.example.cross.card.auth.presentation.register.RegisterScreen
import org.example.cross.card.auth.presentation.register.RegisterViewModel
import org.example.cross.card.auth.presentation.resetPassword.ResetPasswordViewModel
import org.example.cross.card.auth.presentation.resetPassword.RestPasswordScreen
import org.example.cross.card.core.domain.navigation.Destination
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.authRoute() {
    composable<Destination.Auth.Login> {
        val viewModel: LoginViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        LoginScreen(
            state = state,
            onEvent = viewModel::handleEvent,
            modifier = Modifier.fillMaxSize()
        )
    }
    composable<Destination.Auth.Register> {
        val viewModel: RegisterViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        RegisterScreen(
            state = state,
            onEvent = viewModel::handleEvent,
            modifier = Modifier.fillMaxSize()
        )
    }
    composable<Destination.Auth.RestPassword> {
        val viewModel: ResetPasswordViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        RestPasswordScreen(
            state = state,
            onEvent = viewModel::handleEvent,
            modifier = Modifier.fillMaxSize()
        )
    }
}

fun NavGraphBuilder.profileRoute() {
    composable<Destination.Main.Profile> {
        val viewModel: ProfileViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        ProfileScreen(
            state = state,
            onEvent = viewModel::handleEvent,
            modifier = Modifier.fillMaxSize()
        )
    }
}
