package org.example.cross.card.product.presentation.cart

import org.example.cross.card.product.domain.entity.Product

data class CartState(
    val products: List<Product> = emptyList(),
    val totalPrice: String = "",
    val loading: Boolean = true,
)

