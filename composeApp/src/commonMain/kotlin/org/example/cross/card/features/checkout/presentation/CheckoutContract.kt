package org.example.cross.card.features.checkout.presentation

import org.example.cross.card.features.cart.domain.entity.CartItem
import org.example.cross.card.features.checkout.domain.models.Address

data class CheckoutState(
    val items: List<CartItem> = emptyList(),
    val total: Float = 0f,
    val shippingAddress: Address? = null,
    val loading: Boolean = true,
    val showAddressDialog: Boolean = false,
) {
    val validOrder = shippingAddress != null && items.isNotEmpty()
}


sealed class CheckoutEvent {
    data object Checkout : CheckoutEvent()
    data object NavigateBack : CheckoutEvent()
    data class UpdateShippingAddress(val address: Address) : CheckoutEvent()
    data class UpdateAddressDialog(val show: Boolean) : CheckoutEvent()
}