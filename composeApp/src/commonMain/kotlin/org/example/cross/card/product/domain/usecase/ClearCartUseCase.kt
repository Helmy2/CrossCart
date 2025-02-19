package org.example.cross.card.product.domain.usecase

import org.example.cross.card.product.domain.repository.ProductRepo


class ClearCartUseCase(
    private val productRepo: ProductRepo
) {
    suspend operator fun invoke(): Result<Unit> {
        return productRepo.clearCart()
    }
}