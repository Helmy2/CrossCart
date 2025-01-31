package org.example.cross.card.domain.entity


data class User(
    val id: String,
    val name: String,
    val email: String,
    val isAnonymous: Boolean
)