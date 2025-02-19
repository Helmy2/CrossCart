package org.example.cross.card.features.home.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.example.cross.card.features.cart.domain.entity.CategoryWithProducts
import org.example.cross.card.features.details.domain.entity.Category
import org.example.cross.card.features.details.domain.entity.Product
import org.example.cross.card.features.home.domain.repository.HomeRepo

class GetCategoriesWithProductsUseCase(
    private val homeRepo: HomeRepo
) {
    operator fun invoke(): Flow<Result<List<CategoryWithProducts>>> {
        return flow {
            val categories = homeRepo.getAllCategories().getOrThrow()

            val categoriesMap: MutableMap<Category, List<Product>> =
                categories.associateWith<Category, List<Product>> { emptyList() }.toMutableMap()

            emit(Result.success(categoriesMap.map { CategoryWithProducts(it.key, it.value) }))

            for (item in categoriesMap) {
                val products = homeRepo.filterProductsByCategory(item.key.id).getOrThrow()
                categoriesMap[item.key] = products

                emit(Result.success(categoriesMap.map { CategoryWithProducts(it.key, it.value) }))
            }
        }.catch {
            emit(Result.failure(it))
        }
    }
}