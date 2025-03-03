package org.example.cross.card.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.core.util.Connectivity
import org.example.cross.card.features.cart.domain.entity.CategoryWithProducts
import org.example.cross.card.features.home.domain.entity.OrderBy
import org.example.cross.card.features.home.domain.usecase.GetCategoriesWithProductsUseCase
import org.example.cross.card.features.home.domain.usecase.GetProductsByNameUseCase

class HomeViewModel(
    private val getCategoriesWithProductsUseCase: GetCategoriesWithProductsUseCase,
    private val getProductsByNameUseCase: GetProductsByNameUseCase,
    private val snackbarManager: SnackbarManager,
    private val connectivity: Connectivity
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.onStart {
        onEvent(HomeEvent.Refresh)
        viewModelScope.launch {
            connectivity.statusUpdates.collectLatest {
                if (it.isConnected) {
                    onEvent(HomeEvent.Refresh)
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> refresh()
            is HomeEvent.UpdateQuery -> updateQuery(event.query)
            is HomeEvent.SearchProducts -> search()
            is HomeEvent.UpdateExpandedSearch -> updateExpandedSearch(event.expanded)
            is HomeEvent.UpdateFilter -> updateFilter(event.filter)
            is HomeEvent.UpdateOrderBy -> updateOrderBy(event.orderBy)
            is HomeEvent.UpdateFilterDialog -> updateFilterDialog(event.show)
        }
    }

    private fun updateFilterDialog(show: Boolean) {
        _state.update { it.copy(showFilterDialog = show) }

    }

    private fun updateOrderBy(orderBy: OrderBy) {
        _state.update { it.copy(orderBy = orderBy) }
    }

    private fun updateFilter(filter: Filter) {
        _state.update { it.copy(filter = filter) }
    }

    private fun updateExpandedSearch(expanded: Boolean) {
        _state.update { it.copy(expandedSearch = expanded) }
    }

    private fun refresh() {
        viewModelScope.launch {
            getCategoriesWithProductsUseCase().collectLatest { result ->
                result.fold(onSuccess = { categories ->
                    _state.update { it.copy(categories = categories, loading = false) }
                    updatePriceRange(categories)
                    updateRatingRange(categories)
                }, onFailure = { snackbarManager.showErrorSnackbar(it.message.orEmpty()) })
            }
        }
    }

    private fun updateRatingRange(categories: List<CategoryWithProducts>) {
        val minRating =
            categories.flatMap { it.products }.minOfOrNull { it.rating } ?: 0f
        updateFilter(
            state.value.filter.copy(
                rating = minRating
            )
        )
    }

    private fun updatePriceRange(categories: List<CategoryWithProducts>) {
        val minPrice = categories.flatMap { it.products }.minOfOrNull { it.price } ?: 0f
        val maxPrice =
            categories.flatMap { it.products }.maxOfOrNull { it.price } ?: 1000f
        updateFilter(
            state.value.filter.copy(
                defaultPrice = minPrice..maxPrice, price = minPrice..maxPrice
            )
        )
    }

    private fun updateQuery(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(query = query) }
            search()
        }
    }

    private fun search() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = getProductsByNameUseCase(
                query = state.value.query,
                rating = _state.value.filter.rating,
                fromPrice = _state.value.filter.price.start,
                toPrice = _state.value.filter.price.endInclusive,
                orderBy = _state.value.orderBy
            )
            result.fold(onSuccess = { products ->
                _state.update { it.copy(searchProducts = products) }
            }, onFailure = {
                snackbarManager.showErrorSnackbar(it.message.orEmpty())
            })
            _state.update { it.copy(loading = false) }
        }
    }
}

