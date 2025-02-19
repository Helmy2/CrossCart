package org.example.cross.card.features.auth.presentation.resetPassword

import org.jetbrains.compose.resources.StringResource

data class ResetPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val emailError: StringResource? = null,
)

sealed class ResetPasswordEvent {
    data class EmailChanged(val email: String) : ResetPasswordEvent()
    data object ResetPassword : ResetPasswordEvent()
    data object NavigateToLogin : ResetPasswordEvent()
}