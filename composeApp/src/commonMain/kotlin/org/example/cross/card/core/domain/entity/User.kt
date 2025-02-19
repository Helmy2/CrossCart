package org.example.cross.card.core.domain.entity


data class User(
    val id: String,
    val name: String,
    val email: String,
    val profilePicture: String?,
    val isAnonymous: Boolean
)