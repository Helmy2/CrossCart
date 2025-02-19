package org.example.cross.card.features.favorite.presentation

import org.example.cross.card.features.details.domain.entity.Product

data class FavoriteState(
    val products: List<Product> = emptyList(),
    val loading: Boolean = true,
)

