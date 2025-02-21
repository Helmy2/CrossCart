package org.example.cross.card.features.checkout.presentation

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
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.features.cart.domain.usecase.GetAllItemsInCartUseCase
import org.example.cross.card.features.checkout.domain.usecase.CreateOrderUseCase

class CheckoutViewModel(
    private val createOrderUseCase: CreateOrderUseCase,
    private val getAllItemsInCartUseCase: GetAllItemsInCartUseCase,
    private val snackbarManager: SnackbarManager,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(CheckoutState())
    val state: StateFlow<CheckoutState> = _state.onStart {
        load()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CheckoutState())

    fun handleEvent(event: CheckoutEvent) {
        when (event) {
            is CheckoutEvent.Checkout -> checkout()
            CheckoutEvent.NavigateBack -> {
                navigator.navigateAsStart(Destination.Main)
            }
        }
    }

    private fun checkout() {
        viewModelScope.launch {
            snackbarManager.showErrorSnackbar("Checkout is not implemented yet")
        }
    }

    private fun load() {
        viewModelScope.launch {
            getAllItemsInCartUseCase().collectLatest { result ->
                result.fold(
                    onSuccess = { list ->
                        _state.update { checkoutState ->
                            checkoutState.copy(
                                loading = false,
                                items = list,
                                total = list.sumOf { it.product.price.toDouble() * it.quantity }
                                    .toFloat(),
                            )
                        }
                    },
                    onFailure = { snackbarManager.showErrorSnackbar(it.message.orEmpty()) }
                )
            }
        }
    }
}

