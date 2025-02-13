package org.example.cross.card.product.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.product.domain.entity.Category
import org.example.cross.card.product.domain.entity.OrderBy
import org.example.cross.card.product.domain.entity.Product
import org.example.cross.card.product.domain.entity.ProductDetails

interface ProductRepo {
    suspend fun getAllProducts(): Result<List<Product>>
    suspend fun filterProductsByCategory(categoryId: String): Result<List<Product>>
    suspend fun getProductById(productId: String): Result<ProductDetails?>
    suspend fun getAllCategories(): Result<List<Category>>
    suspend fun addToFavorites(productId: String): Result<Unit>
    suspend fun removeFromFavorites(productId: String): Result<Unit>
    suspend fun getFavorites(): Flow<Result<List<Product>>>
    suspend fun getProductsByName(
        query: String,
        rating: Float? = null,
        fromPrice: Float? = null,
        toPrice: Float? = null,
        orderBy: OrderBy = OrderBy.default,
    ): Result<List<Product>>
}