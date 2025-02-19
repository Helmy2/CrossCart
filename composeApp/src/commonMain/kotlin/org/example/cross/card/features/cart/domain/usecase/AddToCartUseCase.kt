package org.example.cross.card.features.cart.domain.usecase

import org.example.cross.card.features.cart.domain.repository.CartRepo

class AddToCartUseCase(
    private val repo: CartRepo
) {
    suspend operator fun invoke(productId: String): Result<Unit> {
        return repo.addToCart(productId)
    }
}