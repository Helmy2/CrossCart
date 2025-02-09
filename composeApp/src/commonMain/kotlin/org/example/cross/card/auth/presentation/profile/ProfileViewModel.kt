package org.example.cross.card.auth.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.cross.card.auth.domain.usecase.CurrentUserFlowUseCase
import org.example.cross.card.auth.domain.usecase.LogoutUseCase
import org.example.cross.card.auth.domain.usecase.UpdateNameUseCase
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager

class ProfileViewModel(
    private val currentUserFlowUseCase: CurrentUserFlowUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val updateNameUseCase: UpdateNameUseCase,
    private val snackbarManager: SnackbarManager,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.onStart {
        loadUser()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileState())

    fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.Logout -> logout()
            is ProfileEvent.UpdateDialog -> updateDialog(event.show)
            is ProfileEvent.UpdateName -> updateName(event.name)
            is ProfileEvent.ConfirmUpdate -> confirmUpdate()
            is ProfileEvent.EditProfile -> editProfile()
        }
    }

    private fun editProfile() {
        _state.update { it.copy(showDialog = true) }
    }

    private fun confirmUpdate() {
        viewModelScope.launch {
            updateDialog(false)
            val result = updateNameUseCase(state.value.name)
            result.fold(
                onSuccess = {
                    snackbarManager.showSnackbar("Name updated successfully")
                },
                onFailure = {
                    snackbarManager.showSnackbar(it.message.orEmpty())
                }
            )
        }
    }

    private fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun updateDialog(show: Boolean) {
        _state.update { it.copy(showDialog = show) }
    }

    private fun loadUser() {
        viewModelScope.launch {
            currentUserFlowUseCase().collect { result ->
                _state.update { it.copy(user = result.getOrNull()) }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            val result = logoutUseCase()
            result.fold(
                onSuccess = {
                    navigator.navigateAsStart(Destination.Auth)
                },
                onFailure = {
                    snackbarManager.showSnackbar(it.message.orEmpty())
                }
            )
        }
    }
}

