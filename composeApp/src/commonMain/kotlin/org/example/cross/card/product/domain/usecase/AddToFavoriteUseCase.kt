package org.example.cross.card.product.domain.usecase

import org.example.cross.card.product.domain.repository.ProductRepo

class AddToFavoriteUseCase(
    private val productRepo: ProductRepo
) {
    suspend operator fun invoke(productId: String): Result<Unit> {
        return productRepo.addToFavorites(productId)
    }
}