package org.example.cross.card.product.domain.usecase

import org.example.cross.card.product.domain.entity.Product
import org.example.cross.card.product.domain.repository.ProductRepo

class GetFavoritesUseCase(
    private val productRepo: ProductRepo
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return productRepo.getFavorites()
    }
}