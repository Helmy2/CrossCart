package org.example.cross.card.features.checkout.presentation

import org.example.cross.card.features.cart.domain.entity.CartItem
import org.example.cross.card.features.checkout.domain.models.Address

data class CheckoutState(
    val items: List<CartItem> = emptyList(),
    val total: Float = 0f,
    val shippingAddress: Address? = null,
    val loading: Boolean = true
)


sealed class CheckoutEvent {
    data object Checkout : CheckoutEvent()
    data object NavigateBack : CheckoutEvent()
}