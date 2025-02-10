package org.example.cross.card.product.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.product.domain.usecase.GetFavoritesUseCase

class FavoriteViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> = _state.onStart {
        refresh()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavoriteState())

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.Refresh -> refresh()
        }
    }


    private fun refresh() {
        viewModelScope.launch {
            getFavoritesUseCase().apply {
                fold(
                    onSuccess = { _state.value = _state.value.copy(products = it) },
                    onFailure = { snackbarManager.showSnackbar(it.message.orEmpty()) }
                )
            }
        }
    }
}

