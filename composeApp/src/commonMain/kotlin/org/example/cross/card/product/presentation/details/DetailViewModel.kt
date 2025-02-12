package org.example.cross.card.product.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.product.domain.usecase.AddToFavoriteUseCase
import org.example.cross.card.product.domain.usecase.GetProductByIdUseCase
import org.example.cross.card.product.domain.usecase.RemoveFromFavoriteUseCase

class DetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    fun handleEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.LoadProduct -> loadProduct(event.productId)
            DetailEvent.ToggleFavorite -> toggleFavorite()
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            val productDetails = state.value.product ?: return@launch

            _state.update {
                it.copy(product = productDetails.copy(isFavorite = !productDetails.isFavorite))
            }

            if (productDetails.isFavorite) {
                removeFromFavoriteUseCase(productDetails.id)
            } else {
                addToFavoriteUseCase(productDetails.id)
            }
        }
    }

    private fun loadProduct(productId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = getProductByIdUseCase(productId)
            result.fold(onSuccess = { _state.value = _state.value.copy(product = it) },
                onFailure = { snackbarManager.showErrorSnackbar(it.message.orEmpty()) })
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}