package org.example.cross.card.product.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.product.domain.usecase.GetProductByIdUseCase

class DetailViewModel(
    val getProductByIdUseCase: GetProductByIdUseCase,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    fun handleEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.LoadProduct -> loadProduct(event.productId)
        }
    }

    private fun loadProduct(productId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = getProductByIdUseCase(productId)
            result.fold(
                onSuccess = { _state.value = _state.value.copy(product = it) },
                onFailure = { snackbarManager.showSnackbar(it.message.orEmpty()) }
            )
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}