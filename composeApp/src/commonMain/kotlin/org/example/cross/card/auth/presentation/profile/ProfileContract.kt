package org.example.cross.card.auth.presentation.profile

import io.github.vinceglb.filekit.core.PlatformFile
import org.example.cross.card.auth.domain.entity.User

data class ProfileState(
    val user: User? = null,
    val showEditNameDialog: Boolean = false,
    val showEditProfilePictureDialog: Boolean = false,
    val name: String = "",
    val profilePictureLoading: Boolean = false
)

sealed class ProfileEvent {
    data object Logout : ProfileEvent()
    data class EditeNameDialog(val show: Boolean) : ProfileEvent()
    data class EditeProfilePictureDialog(val show: Boolean) : ProfileEvent()
    data class UpdateName(val name: String) : ProfileEvent()
    data object ConfirmUpdateName : ProfileEvent()
    data class ConfirmUpdateProfilePicture(val file: PlatformFile) : ProfileEvent()
}