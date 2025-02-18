package org.example.cross.card.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.error_invalid_email
import crosscart.composeapp.generated.resources.error_invalid_password
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.cross.card.auth.domain.usecase.IsUserLongedInFlowUseCase
import org.example.cross.card.auth.domain.usecase.LoginUseCase
import org.example.cross.card.auth.domain.usecase.SignInAnonymouslyUseCase
import org.example.cross.card.auth.domain.util.isValidEmail
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val signInAnonymouslyUseCase: SignInAnonymouslyUseCase,
    private val isUserLongedInFlowUseCase: IsUserLongedInFlowUseCase,
    private val snackbarManager: SnackbarManager,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.onStart {
        observeCurrentUser()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoginState())

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> updateEmail(event.email)
            is LoginEvent.PasswordChanged -> updatePassword(event.password)
            LoginEvent.TogglePasswordVisibility -> togglePasswordVisibility()
            LoginEvent.SignInAnonymously -> signInAnonymously()
            LoginEvent.Login -> login()
            LoginEvent.NavigateToRegister -> navigateToRegister()
            LoginEvent.NavigateToRestPassword -> navigateToRestPassword()
            is LoginEvent.GoogleLogin -> googleLogin(event.result)
        }
    }

    // Observe current user for the desktop login with google
    private fun observeCurrentUser() {
        viewModelScope.launch {
            isUserLongedInFlowUseCase().collectLatest {
                if (it) {
                    navigator.navigate(Destination.Main)
                }
            }
        }
    }

    private fun googleLogin(result: Result<Unit>) {
        viewModelScope.launch {
            handleAuthResult(result) {
                navigator.navigate(Destination.Main)
            }
        }
    }

    private fun updateEmail(value: String) {
        _state.update { it.copy(email = value, emailError = null) }
    }

    private fun updatePassword(value: String) {
        _state.update { it.copy(password = value, passwordError = null) }
    }

    private fun navigateToRegister() {
        navigator.navigate(Destination.Auth.Register)
    }

    private fun navigateToRestPassword() {
        navigator.navigate(Destination.Auth.RestPassword)
    }

    private fun togglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun login() {
        if (!validateLoginInputs()) return

        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = loginUseCase(state.value.email, state.value.password)
            handleAuthResult(result) {
                navigator.navigate(Destination.Main)
            }
        }
    }


    private fun signInAnonymously() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = signInAnonymouslyUseCase()
            handleAuthResult(result) {
                navigator.navigate(Destination.Main)
            }
        }
    }

    private suspend fun handleAuthResult(result: Result<Unit>, onSuccess: suspend () -> Unit) {
        _state.update { it.copy(loading = false) }
        result.fold(
            onSuccess = { onSuccess() },
            onFailure = { snackbarManager.showErrorSnackbar(it.message.orEmpty()) },
        )
    }

    // Enhanced validation
    private fun validateLoginInputs(): Boolean {
        val emailValid = isValidEmail(state.value.email)
        val passwordValid = state.value.password.length >= 8

        _state.update {
            it.copy(
                emailError = if (emailValid) null else Res.string.error_invalid_email,
                passwordError = if (passwordValid) null else Res.string.error_invalid_password,
            )
        }

        return emailValid && passwordValid
    }
}

