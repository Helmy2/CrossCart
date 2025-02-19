package org.example.cross.card.cart.domain.usecase

import org.example.cross.card.cart.domain.repository.CartRepo

class RemoveFromCartUseCase(
    private val repo: CartRepo
) {
    suspend operator fun invoke(productId: String): Result<Unit> {
        return repo.removeFromCart(productId)
    }
}