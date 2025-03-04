package org.example.cross.card.features.home.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.cross.card.features.cart.domain.entity.CategoryWithProducts
import org.example.cross.card.features.home.domain.repository.HomeRepo

class GetCategoriesWithProductsUseCase(
    private val homeRepo: HomeRepo
) {
    operator fun invoke(): Flow<List<CategoryWithProducts>> = homeRepo.getAllProducts().map {
        it.groupBy { product -> product.categoryName }.map { entry ->
            CategoryWithProducts(
                categoryName = entry.key,
                entry.value
            )
        }
    }
}