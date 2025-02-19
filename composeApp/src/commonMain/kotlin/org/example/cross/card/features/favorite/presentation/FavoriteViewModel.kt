package org.example.cross.card.features.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.features.favorite.domain.usecase.GetFavoritesUseCase

class FavoriteViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> = _state.onStart {
        load()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavoriteState())

    private fun load() {
        viewModelScope.launch {
            getFavoritesUseCase().collectLatest { result ->
                result.fold(
                    onSuccess = {
                        _state.value = _state.value.copy(products = it, loading = false)
                    },
                    onFailure = { snackbarManager.showErrorSnackbar(it.message.orEmpty()) }
                )
            }
        }
    }
}

