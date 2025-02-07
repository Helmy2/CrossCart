package org.example.cross.card.product.presentation.details

import org.example.cross.card.product.domain.entity.ProductDetails

data class DetailState(
    val product: ProductDetails? = null,
    val isLoading: Boolean = true,
)

sealed class DetailEvent {
    data class LoadProduct(val productId: String) : DetailEvent()
}