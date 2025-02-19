package org.example.cross.card.product.presentation.cart

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
import org.example.cross.card.core.util.format
import org.example.cross.card.product.domain.usecase.GetAllItemsInCartUseCase

class CartViewModel(
    private val getAllItemsInCartUseCase: GetAllItemsInCartUseCase,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.onStart {
        load()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CartState())

    private fun load() {
        viewModelScope.launch {
            getAllItemsInCartUseCase().collectLatest { result ->
                result.fold(
                    onSuccess = { list ->
                        _state.value = _state.value.copy(
                            products = list,
                            totalPrice = list.sumOf { it.price.toDouble() }.toFloat().format(),
                            loading = false,

                            )
                    },
                    onFailure = { snackbarManager.showErrorSnackbar(it.message.orEmpty()) }
                )
            }
        }
    }
}

