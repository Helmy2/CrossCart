package org.example.cross.card.auth.domain.entity


data class User(
    val id: String,
    val name: String,
    val email: String,
    val profilePicture: String?,
    val isAnonymous: Boolean
)