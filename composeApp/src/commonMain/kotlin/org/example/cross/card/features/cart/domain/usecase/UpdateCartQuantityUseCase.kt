package org.example.cross.card.features.cart.domain.usecase

import org.example.cross.card.features.cart.domain.repository.CartRepo


class UpdateCartQuantityUseCase(
    private val repo: CartRepo
) {
    suspend operator fun invoke(productId: String, quantity: Int): Result<Unit> {
        return repo.updateCartQuantity(productId, quantity)
    }
}