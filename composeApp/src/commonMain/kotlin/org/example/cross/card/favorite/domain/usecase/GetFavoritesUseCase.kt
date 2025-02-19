package org.example.cross.card.favorite.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.details.domain.entity.Product
import org.example.cross.card.favorite.domain.repository.FavoriteRepo

class GetFavoritesUseCase(
    private val repo: FavoriteRepo,
) {
    suspend operator fun invoke(): Flow<Result<List<Product>>> {
        return repo.getFavorites()
    }
}