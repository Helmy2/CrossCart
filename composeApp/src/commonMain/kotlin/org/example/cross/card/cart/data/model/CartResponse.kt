package org.example.cross.card.cart.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    @SerialName("user_id")
    val userId: String,
    @SerialName("product_id")
    val productId: String,
    val amount: Int
)