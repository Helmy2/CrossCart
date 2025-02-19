package org.example.cross.card.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.profile.domain.usecase.CurrentUserFlowUseCase
import org.example.cross.card.profile.domain.usecase.LogoutUseCase
import org.example.cross.card.profile.domain.usecase.UpdateNameUseCase
import org.example.cross.card.profile.domain.usecase.UpdateProfilePictureUseCase

class ProfileViewModel(
    private val currentUserFlowUseCase: CurrentUserFlowUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val updateNameUseCase: UpdateNameUseCase,
    private val updateProfilePictureUseCase: UpdateProfilePictureUseCase,
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
            is ProfileEvent.EditeNameDialog -> updateEditeNameDialog(event.show)
            is ProfileEvent.UpdateName -> updateName(event.name)
            is ProfileEvent.ConfirmUpdateName -> confirmUpdate()
            is ProfileEvent.ConfirmUpdateProfilePicture -> confirmUpdateProfilePicture(event.file)
            is ProfileEvent.EditeProfilePictureDialog -> updateEditeProfilePictureDialog(event.show)
        }
    }

    private fun confirmUpdateProfilePicture(file: PlatformFile) {
        viewModelScope.launch {
            updateEditeProfilePictureDialog(false)
            updateProfilePictureUseCase(file).collect { result ->
                result.fold(
                    onSuccess = { uploadProgress ->
                        if (uploadProgress < 100f) {
                            _state.update { it.copy(profilePictureLoading = true) }
                        } else {
                            _state.update { it.copy(profilePictureLoading = false) }
                        }
                    },
                    onFailure = {
                        snackbarManager.showErrorSnackbar(it.message.orEmpty())
                    }
                )
            }
        }
    }

    private fun updateEditeProfilePictureDialog(show: Boolean) {
        _state.update { it.copy(showEditProfilePictureDialog = show) }
    }


    private fun confirmUpdate() {
        viewModelScope.launch {
            updateEditeNameDialog(false)
            val result = updateNameUseCase(state.value.name)
            result.fold(
                onSuccess = {
                    snackbarManager.showErrorSnackbar("Name updated successfully")
                },
                onFailure = {
                    snackbarManager.showErrorSnackbar(it.message.orEmpty())
                }
            )
        }
    }

    private fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun updateEditeNameDialog(show: Boolean) {
        _state.update { it.copy(showEditNameDialog = show) }
    }

    private fun loadUser() {
        viewModelScope.launch {
            currentUserFlowUseCase().collectLatest { result ->
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
                    snackbarManager.showErrorSnackbar(it.message.orEmpty())
                }
            )
        }
    }
}

