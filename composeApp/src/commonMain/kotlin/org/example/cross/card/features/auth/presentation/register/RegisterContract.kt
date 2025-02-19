package org.example.cross.card.features.auth.presentation.register

import org.example.cross.card.features.auth.domain.entity.PasswordStrength
import org.example.cross.card.features.auth.domain.entity.Requirement
import org.jetbrains.compose.resources.StringResource

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val isLoading: Boolean = false,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
    val nameError: StringResource? = null,
    val isPasswordVisible: Boolean = false,
    val passwordStrength: PasswordStrength = PasswordStrength.WEAK,
    val passwordRequirements: List<Requirement> = emptyList()
)

sealed class RegisterEvent {
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data class NameChanged(val name: String) : RegisterEvent()
    data object TogglePasswordVisibility : RegisterEvent()
    data object Register : RegisterEvent()
    data object NavigateToLogin : RegisterEvent()
}