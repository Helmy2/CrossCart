package org.example.cross.card.details.domain.usecase

import org.example.cross.card.details.domain.entity.ProductDetails
import org.example.cross.card.details.domain.repository.DetailsRepo

class GetProductDetailsUseCase(
    private val repo: DetailsRepo
) {
    suspend operator fun invoke(id: String): Result<ProductDetails?> {
        return repo.getProductById(id)
    }
}