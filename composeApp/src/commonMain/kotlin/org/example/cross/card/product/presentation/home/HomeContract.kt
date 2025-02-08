package org.example.cross.card.product.presentation.home

import org.example.cross.card.product.domain.entity.CategoryWithProducts
import org.example.cross.card.product.domain.entity.Product

data class HomeState(
    val query: String = "",
    val searchProducts: List<Product> = emptyList(),
    val categories: List<CategoryWithProducts> = emptyList(),
    val isLoading: Boolean = false,
    val expandedSearch: Boolean = false,
)

sealed class HomeEvent {
    data object Refresh : HomeEvent()
    data class SearchProducts(val query: String) : HomeEvent()
    data class UpdateQuery(val query: String) : HomeEvent()
    data class UpdateExpandedSearch(val expanded: Boolean) : HomeEvent()
}

