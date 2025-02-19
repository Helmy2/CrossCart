package org.example.cross.card.product.domain.usecase

import org.example.cross.card.details.domain.entity.Product
import org.example.cross.card.product.domain.entity.OrderBy
import org.example.cross.card.product.domain.repository.ProductRepo

class GetProductsByNameUseCase(
    private val productRepo: ProductRepo
) {
    suspend operator fun invoke(
        query: String,
        rating: Float? = null,
        fromPrice: Float? = null,
        toPrice: Float? = null,
        orderBy: OrderBy = OrderBy.default
    ): Result<List<Product>> {
        return productRepo.getProductsByName(query, rating, fromPrice, toPrice, orderBy)
    }
}