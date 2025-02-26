package org.example.cross.card.features.checkout.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
)