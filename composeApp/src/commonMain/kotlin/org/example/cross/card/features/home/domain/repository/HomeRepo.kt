package org.example.cross.card.features.home.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.features.details.domain.entity.Category
import org.example.cross.card.features.details.domain.entity.Product
import org.example.cross.card.features.home.domain.entity.OrderBy

interface HomeRepo {
    fun getAllProducts(): Flow<List<Product>>
    suspend fun filterProductsByCategory(categoryId: String): Result<List<Product>>
    suspend fun getAllCategories(): Result<List<Category>>
    suspend fun getProductsByName(
        query: String,
        rating: Float? = null,
        fromPrice: Float? = null,
        toPrice: Float? = null,
        orderBy: OrderBy = OrderBy.default,
    ): Result<List<Product>>
}