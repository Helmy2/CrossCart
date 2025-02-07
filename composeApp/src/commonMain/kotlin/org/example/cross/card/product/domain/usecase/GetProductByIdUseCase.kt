package org.example.cross.card.product.domain.usecase

import org.example.cross.card.product.domain.entity.ProductDetails
import org.example.cross.card.product.domain.repository.ProductRepo

class GetProductByIdUseCase(
    private val productRepo: ProductRepo
) {
    suspend operator fun invoke(id: String): Result<ProductDetails?> {
        return productRepo.getProductById(id)
    }
}