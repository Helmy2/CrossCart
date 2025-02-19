package org.example.cross.card.features.home.domain.usecase

import org.example.cross.card.features.details.domain.entity.Product
import org.example.cross.card.features.home.domain.entity.OrderBy
import org.example.cross.card.features.home.domain.repository.HomeRepo

class GetProductsByNameUseCase(
    private val homeRepo: HomeRepo
) {
    suspend operator fun invoke(
        query: String,
        rating: Float? = null,
        fromPrice: Float? = null,
        toPrice: Float? = null,
        orderBy: OrderBy = OrderBy.default
    ): Result<List<Product>> {
        return homeRepo.getProductsByName(query, rating, fromPrice, toPrice, orderBy)
    }
}