package org.example.cross.card.product.domain.usecase

import org.example.cross.card.product.domain.entity.Product
import org.example.cross.card.product.domain.repository.ProductRepo

class GetProductsByNameUseCase(
    private val productRepo: ProductRepo
) {
    suspend operator fun invoke(name: String): Result<List<Product>> {
        return productRepo.getProductsByName(name)
    }
}