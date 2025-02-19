package org.example.cross.card.features.cart.domain.usecase

import org.example.cross.card.features.cart.domain.repository.CartRepo


class ClearCartUseCase(
    private val repo: CartRepo
) {
    suspend operator fun invoke(): Result<Unit> {
        return repo.clearCart()
    }
}