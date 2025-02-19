package org.example.cross.card.product.presentation.cart

import org.example.cross.card.product.domain.entity.CartItem

data class CartState(
    val products: List<CartItem> = emptyList(),
    val totalPrice: String = "",
    val loading: Boolean = true,
    val syncing: Boolean = false
)

sealed class CartEvent {
    data class RemoveFromCart(val item: CartItem) : CartEvent()
    data class IncreaseQuantity(val item: CartItem) : CartEvent()
    data class DecreaseQuantity(val item: CartItem) : CartEvent()
    data object ClearAll : CartEvent()
}

