package org.example.cross.card.features.auth.presentation.resetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.error_invalid_email
import crosscart.composeapp.generated.resources.reset_password_success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.core.util.isValidEmail
import org.example.cross.card.features.auth.domain.usecase.ResetPasswordUseCase
import org.jetbrains.compose.resources.getString

class ResetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val snackbarManager: SnackbarManager,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(ResetPasswordState())
    val state: StateFlow<ResetPasswordState> = _state

    fun handleEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.EmailChanged -> updateEmail(event.email)
            ResetPasswordEvent.ResetPassword -> resetPassword()
            ResetPasswordEvent.NavigateToLogin -> navigateToLogin()
        }
    }

    private fun updateEmail(value: String) {
        _state.update { it.copy(email = value, emailError = null) }
    }


    private fun navigateToLogin() {
        navigator.navigateBack()
    }

    private fun resetPassword() {
        if (!isValidEmail(_state.value.email)) {
            _state.update { it.copy(emailError = Res.string.error_invalid_email) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = resetPasswordUseCase(state.value.email)
            handleAuthResult(result) {
                navigateToLogin()
                snackbarManager.showErrorSnackbar(getString(Res.string.reset_password_success))
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
}

