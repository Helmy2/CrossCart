package org.example.cross.card.auth.presentation.login

import org.jetbrains.compose.resources.StringResource

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val isPasswordVisible: Boolean = false,
)

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data object TogglePasswordVisibility : LoginEvent()
    data object SignInAnonymously : LoginEvent()
    data object Login : LoginEvent()
    data object NavigateToRegister : LoginEvent()
    data object NavigateToRestPassword : LoginEvent()
}