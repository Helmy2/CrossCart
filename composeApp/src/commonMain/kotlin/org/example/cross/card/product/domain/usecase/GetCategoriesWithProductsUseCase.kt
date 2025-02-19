package org.example.cross.card.product.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.example.cross.card.cart.domain.entity.CategoryWithProducts
import org.example.cross.card.details.domain.entity.Category
import org.example.cross.card.details.domain.entity.Product
import org.example.cross.card.product.domain.repository.ProductRepo

class GetCategoriesWithProductsUseCase(
    private val productRepo: ProductRepo
) {
    operator fun invoke(): Flow<Result<List<CategoryWithProducts>>> {
        return flow {
            val categories = productRepo.getAllCategories().getOrThrow()

            val categoriesMap: MutableMap<Category, List<Product>> =
                categories.associateWith<Category, List<Product>> { emptyList() }.toMutableMap()

            emit(Result.success(categoriesMap.map { CategoryWithProducts(it.key, it.value) }))

            for (item in categoriesMap) {
                val products = productRepo.filterProductsByCategory(item.key.id).getOrThrow()
                categoriesMap[item.key] = products

                emit(Result.success(categoriesMap.map { CategoryWithProducts(it.key, it.value) }))
            }
        }.catch {
            emit(Result.failure(it))
        }
    }
}