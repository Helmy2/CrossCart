package org.example.cross.card.product.presentation.home

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
import org.example.cross.card.product.domain.usecase.GetCategoriesWithProductsUseCase
import org.example.cross.card.product.domain.usecase.GetProductsByNameUseCase

class HomeViewModel(
    private val getCategoriesWithProductsUseCase: GetCategoriesWithProductsUseCase,
    private val getProductsByNameUseCase: GetProductsByNameUseCase,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.onStart {
        refresh()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> refresh()
            is HomeEvent.UpdateQuery -> updateQuery(event.query)
            is HomeEvent.SearchProducts -> search(event.query)
            is HomeEvent.UpdateExpandedSearch -> updateExpandedSearch(event.expanded)
        }
    }

    private fun updateExpandedSearch(expanded: Boolean) {
        _state.update { it.copy(expandedSearch = expanded) }
    }

    private fun refresh() {
        viewModelScope.launch {
            getCategoriesWithProductsUseCase().collectLatest { result ->
                result.fold(
                    onSuccess = { categories ->
                        _state.update { it.copy(categories = categories, loading = false) }
                    },
                    onFailure = { snackbarManager.showSnackbar(it.message.orEmpty()) }
                )
            }
        }
    }

    private fun updateQuery(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(query = query) }
            search(query)
        }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val result = getProductsByNameUseCase(query)
            result.fold(
                onSuccess = { products ->
                    _state.update { it.copy(searchProducts = products) }
                },
                onFailure = {
                    snackbarManager.showSnackbar(it.message.orEmpty())
                }
            )
            _state.update { it.copy(loading = false) }
        }
    }
}

