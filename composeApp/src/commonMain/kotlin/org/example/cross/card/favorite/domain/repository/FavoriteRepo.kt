package org.example.cross.card.favorite.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.product.domain.entity.Product

interface FavoriteRepo {
    suspend fun addToFavorites(productId: String): Result<Unit>
    suspend fun removeFromFavorites(productId: String): Result<Unit>
    suspend fun getFavorites(): Flow<Result<List<Product>>>
}