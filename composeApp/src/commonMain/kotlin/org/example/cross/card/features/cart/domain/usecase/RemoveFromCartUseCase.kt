package org.example.cross.card.features.cart.domain.usecase

import org.example.cross.card.features.cart.domain.repository.CartRepo

class RemoveFromCartUseCase(
    private val repo: CartRepo
) {
    suspend operator fun invoke(productId: String): Result<Unit> {
        return repo.removeFromCart(productId)
    }
}