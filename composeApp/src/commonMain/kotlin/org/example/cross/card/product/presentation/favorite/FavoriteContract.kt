package org.example.cross.card.product.presentation.favorite

import org.example.cross.card.product.domain.entity.Product

data class FavoriteState(
    val products: List<Product> = emptyList(),
    val loading: Boolean = true,
)

