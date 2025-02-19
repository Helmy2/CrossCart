package org.example.cross.card.favorite.domain.usecase

import org.example.cross.card.favorite.domain.repository.FavoriteRepo

class RemoveFromFavoriteUseCase(
    private val repo: FavoriteRepo,
) {
    suspend operator fun invoke(productId: String): Result<Unit> {
        return repo.removeFromFavorites(productId)
    }
}