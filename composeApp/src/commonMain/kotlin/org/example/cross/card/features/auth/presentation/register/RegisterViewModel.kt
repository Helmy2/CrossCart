package org.example.cross.card.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.create_account_success
import crosscart.composeapp.generated.resources.error_invalid_email
import crosscart.composeapp.generated.resources.error_name_required
import crosscart.composeapp.generated.resources.error_password_empty
import crosscart.composeapp.generated.resources.error_password_weak
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.core.util.calculatePasswordRequirements
import org.example.cross.card.core.util.calculatePasswordStrength
import org.example.cross.card.core.util.isValidEmail
import org.example.cross.card.features.auth.domain.entity.PasswordStrength
import org.example.cross.card.features.auth.domain.usecase.RegisterUseCase
import org.jetbrains.compose.resources.getString

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val snackbarManager: SnackbarManager,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun handleEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> updateEmail(event.email)
            is RegisterEvent.PasswordChanged -> updatePassword(event.password)
            is RegisterEvent.NameChanged -> updateName(event.name)
            RegisterEvent.TogglePasswordVisibility -> togglePasswordVisibility()
            RegisterEvent.Register -> register()
            RegisterEvent.NavigateToLogin -> navigateToLogin()
        }
    }

    private fun updateEmail(value: String) {
        _state.update { it.copy(email = value, emailError = null) }
    }

    private fun updatePassword(value: String) {
        val strength = calculatePasswordStrength(value)
        val requirements = calculatePasswordRequirements(value)

        _state.update {
            it.copy(
                passwordStrength = strength,
                passwordRequirements = requirements,
                password = value, passwordError = null
            )
        }
    }

    private fun updateName(value: String) {
        _state.update { it.copy(name = value, nameError = null) }
    }

    private fun navigateToLogin() {
        navigator.navigateBack()
    }

    private fun togglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun register() {
        if (!validateRegisterInputs()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = registerUseCase(
                email = state.value.email, password = state.value.password, name = state.value.name
            )
            handleAuthResult(result) {
                navigateToLogin()
                snackbarManager.showErrorSnackbar(getString(Res.string.create_account_success))
            }
        }
    }

    private suspend fun handleAuthResult(result: Result<Unit>, onSuccess: suspend () -> Unit) {
        _state.update { it.copy(isLoading = false) }
        result.fold(
            onSuccess = { onSuccess() },
            onFailure = { snackbarManager.showErrorSnackbar(it.message.orEmpty()) },
        )
    }

    private fun validateRegisterInputs(): Boolean {
        val emailValid = isValidEmail(state.value.email)
        val passwordValid = calculatePasswordStrength(state.value.password) != PasswordStrength.WEAK
        val nameValid = state.value.name.isNotBlank()

        _state.update {
            it.copy(
                emailError = if (emailValid) null else Res.string.error_invalid_email,
                passwordError = when {
                    passwordValid -> null
                    state.value.password.isBlank() -> Res.string.error_password_empty
                    else -> Res.string.error_password_weak
                },
                nameError = if (nameValid) null else Res.string.error_name_required
            )
        }
        return emailValid && passwordValid && nameValid
    }
}

