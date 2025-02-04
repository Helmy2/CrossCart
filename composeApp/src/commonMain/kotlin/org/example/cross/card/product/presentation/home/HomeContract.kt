package org.example.cross.card.product.presentation.home

import org.example.cross.card.product.domain.entity.CategoryWithProducts

data class HomeState(
    val categories: List<CategoryWithProducts> = emptyList(),
    val isLoading: Boolean = false,
)

sealed class HomeEvent {

}

