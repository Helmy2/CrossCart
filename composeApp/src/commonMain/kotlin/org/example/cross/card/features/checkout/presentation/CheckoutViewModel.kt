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
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.features.cart.domain.usecase.GetAllItemsInCartUseCase
import org.example.cross.card.features.checkout.data.models.OrderItem
import org.example.cross.card.features.checkout.domain.models.Address
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
            CheckoutEvent.NavigateBack -> navigateBack()
            is CheckoutEvent.UpdateAddressDialog -> updateAddressDialog(event.show)
            is CheckoutEvent.UpdateShippingAddress -> updateShippingAddress(event.address)
        }
    }

    private fun updateAddressDialog(show: Boolean) {
        _state.update { it.copy(showAddressDialog = show) }
    }

    private fun updateShippingAddress(address: Address) {
        _state.update { it.copy(showAddressDialog = false, shippingAddress = address) }
    }

    private fun navigateBack() {
        navigator.navigateBack()
    }

    private fun checkout() {
        state.value.shippingAddress?.let {
            viewModelScope.launch {
                val result = createOrderUseCase(
                    orderItems = state.value.items.map {
                        OrderItem(it.product.id, it.quantity, itemPrice = it.product.price)
                    },
                    shippingAddress = it
                )
                result.fold(
                    onSuccess = {
                        snackbarManager.showSnackbar("Order placed successfully")
                        navigateBack()
                    },
                    onFailure = {
                        snackbarManager.showErrorSnackbar(it.message.orEmpty())
                    }
                )
            }
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

