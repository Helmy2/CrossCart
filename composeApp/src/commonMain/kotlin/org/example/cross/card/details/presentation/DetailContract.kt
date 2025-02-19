package org.example.cross.card.details.presentation

import org.example.cross.card.details.domain.entity.ProductDetails

data class DetailState(
    val product: ProductDetails? = null,
    val isLoading: Boolean = true,
)

sealed class DetailEvent {
    data class LoadProduct(val productId: String) : DetailEvent()
    data object ToggleFavorite : DetailEvent()
    data object ToggleInCart : DetailEvent()
}