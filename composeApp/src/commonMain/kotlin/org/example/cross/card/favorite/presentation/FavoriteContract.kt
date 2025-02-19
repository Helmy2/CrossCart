package org.example.cross.card.favorite.presentation

import org.example.cross.card.details.domain.entity.Product

data class FavoriteState(
    val products: List<Product> = emptyList(),
    val loading: Boolean = true,
)

