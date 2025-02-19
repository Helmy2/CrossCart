package org.example.cross.card.product.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.product.domain.entity.Product
import org.example.cross.card.product.domain.repository.ProductRepo

class GetAllItemsInCartUseCase(
    private val productRepo: ProductRepo
) {
    suspend operator fun invoke(): Flow<Result<List<Product>>> {
        return productRepo.getAllItemsInCart()
    }
}