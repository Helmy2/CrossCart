package org.example.cross.card.features.home.presentation

import org.example.cross.card.features.cart.domain.entity.CategoryWithProducts
import org.example.cross.card.features.details.domain.entity.Product
import org.example.cross.card.features.home.domain.entity.OrderBy

data class HomeState(
    val query: String = "",
    val searchProducts: List<Product> = emptyList(),
    val orderBy: OrderBy = OrderBy.default,
    val filter: Filter = Filter(),
    val categories: List<CategoryWithProducts> = emptyList(),
    val loading: Boolean = true,
    val expandedSearch: Boolean = false,
    val showFilterDialog: Boolean = false
)

sealed class HomeEvent {
    data object Refresh : HomeEvent()
    data object SearchProducts : HomeEvent()
    data class UpdateQuery(val query: String) : HomeEvent()
    data class UpdateExpandedSearch(val expanded: Boolean) : HomeEvent()
    data class UpdateOrderBy(val orderBy: OrderBy) : HomeEvent()
    data class UpdateFilter(val filter: Filter) : HomeEvent()
    data class UpdateFilterDialog(val show: Boolean) : HomeEvent()
}

data class Filter(
    val rating: Float? = null,
    val price: ClosedFloatingPointRange<Float> = 0f..1000f,
    val defaultPrice: ClosedFloatingPointRange<Float> = 0f..1000f
)