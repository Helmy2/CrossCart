package org.example.cross.card.features.favorite.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteResponse(
    @SerialName("user_id")
    val userId: String,
    @SerialName("product_id")
    val productId: String
)