package org.example.cross.card.favorite.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.favorite.domain.repository.FavoriteRepo
import org.example.cross.card.product.domain.entity.Product

class GetFavoritesUseCase(
    private val repo: FavoriteRepo,
) {
    suspend operator fun invoke(): Flow<Result<List<Product>>> {
        return repo.getFavorites()
    }
}