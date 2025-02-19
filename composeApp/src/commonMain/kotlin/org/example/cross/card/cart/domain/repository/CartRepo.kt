package org.example.cross.card.cart.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.cart.domain.entity.CartItem

interface CartRepo {
    suspend fun addToCart(productId: String): Result<Unit>
    suspend fun removeFromCart(productId: String): Result<Unit>
    suspend fun getAllItemsInCart(): Flow<Result<List<CartItem>>>
    suspend fun updateCartQuantity(productId: String, quantity: Int): Result<Unit>
    suspend fun clearCart(): Result<Unit>
}