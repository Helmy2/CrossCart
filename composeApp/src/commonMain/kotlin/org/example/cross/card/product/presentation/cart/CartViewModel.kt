package org.example.cross.card.product.presentation.cart

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
import org.example.cross.card.core.util.format
import org.example.cross.card.product.domain.entity.CartItem
import org.example.cross.card.product.domain.usecase.GetAllItemsInCartUseCase
import org.example.cross.card.product.domain.usecase.RemoveFromCartUseCase
import org.example.cross.card.product.domain.usecase.UpdateCartQuantityUseCase

class CartViewModel(
    private val getAllItemsInCartUseCase: GetAllItemsInCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.onStart {
        load()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CartState())

    fun handleEvent(event: CartEvent) {
        when (event) {
            is CartEvent.DecreaseQuantity -> decrease(event.item)
            is CartEvent.IncreaseQuantity -> increase(event.item)
            is CartEvent.RemoveFromCart -> remove(event.item)
        }
    }

    private fun decrease(item: CartItem) {
        viewModelScope.launch {
            if (item.quantity == 1) {
                return@launch
            }
            _state.update { it.copy(syncing = true) }
            val result = updateCartQuantityUseCase(item.product.id, item.quantity - 1)
            if (result.isFailure) {
                snackbarManager.showErrorSnackbar(result.exceptionOrNull()?.message.orEmpty())
            }
        }
    }

    private fun increase(item: CartItem) {
        viewModelScope.launch {
            val result = updateCartQuantityUseCase(item.product.id, item.quantity + 1)
            _state.update { it.copy(syncing = true) }
            if (result.isFailure) {
                snackbarManager.showErrorSnackbar(result.exceptionOrNull()?.message.orEmpty())
            }
        }
    }

    private fun remove(cartItem: CartItem) {
        viewModelScope.launch {
            removeFromCartUseCase(cartItem.product.id)
        }
    }

    private fun load() {
        viewModelScope.launch {
            getAllItemsInCartUseCase().collectLatest { result ->
                result.fold(
                    onSuccess = { list ->
                        _state.value = _state.value.copy(
                            products = list,
                            totalPrice = list.sumOf { it.product.price.toDouble() * it.quantity }
                                .toFloat()
                                .format(),
                            loading = false,
                            syncing = false
                        )
                    },
                    onFailure = { snackbarManager.showErrorSnackbar(it.message.orEmpty()) }
                )
            }
        }
    }
}

