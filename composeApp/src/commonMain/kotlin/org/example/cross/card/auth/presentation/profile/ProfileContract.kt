package org.example.cross.card.auth.presentation.profile

import org.example.cross.card.auth.domain.entity.User

data class ProfileState(
    val user: User? = null,
    val showDialog: Boolean = false,
    val name: String = ""
)

sealed class ProfileEvent {
    data object Logout : ProfileEvent()
    data object EditProfile : ProfileEvent()
    data class UpdateDialog(val show: Boolean) : ProfileEvent()
    data class UpdateName(val name: String) : ProfileEvent()
    data object ConfirmUpdate : ProfileEvent()
}