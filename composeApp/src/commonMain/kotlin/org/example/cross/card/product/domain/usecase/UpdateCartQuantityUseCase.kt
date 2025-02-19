package org.example.cross.card.product.domain.usecase

import org.example.cross.card.product.domain.repository.ProductRepo


class UpdateCartQuantityUseCase(
    private val productRepo: ProductRepo
) {
    suspend operator fun invoke(productId: String, quantity: Int): Result<Unit> {
        return productRepo.updateCartQuantity(productId, quantity)
    }
}