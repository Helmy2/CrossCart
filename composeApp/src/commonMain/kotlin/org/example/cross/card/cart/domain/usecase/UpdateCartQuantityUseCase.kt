package org.example.cross.card.cart.domain.usecase

import org.example.cross.card.cart.domain.repository.CartRepo


class UpdateCartQuantityUseCase(
    private val repo: CartRepo
) {
    suspend operator fun invoke(productId: String, quantity: Int): Result<Unit> {
        return repo.updateCartQuantity(productId, quantity)
    }
}