package org.example.cross.card.features.details.domain.usecase

import org.example.cross.card.features.details.domain.entity.ProductDetails
import org.example.cross.card.features.details.domain.repository.DetailsRepo

class GetProductDetailsUseCase(
    private val repo: DetailsRepo
) {
    suspend operator fun invoke(id: String): Result<ProductDetails?> {
        return repo.getProductById(id)
    }
}