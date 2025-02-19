package org.example.cross.card.cart.domain.entity

import org.example.cross.card.product.domain.entity.Product

data class CartItem(
    val product: Product,
    val quantity: Int,
)