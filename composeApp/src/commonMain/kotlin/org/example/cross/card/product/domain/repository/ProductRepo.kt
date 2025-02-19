package org.example.cross.card.product.domain.repository

import org.example.cross.card.details.domain.entity.Category
import org.example.cross.card.details.domain.entity.Product
import org.example.cross.card.product.domain.entity.OrderBy

interface ProductRepo {
    suspend fun getAllProducts(): Result<List<Product>>
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