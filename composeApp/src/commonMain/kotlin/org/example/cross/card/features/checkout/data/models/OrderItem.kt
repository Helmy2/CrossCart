package org.example.cross.card.features.checkout.data.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val itemId: String,
    val quantity: Int,
    val itemPrice: Float,
)