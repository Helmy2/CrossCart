package org.example.cross.card.features.cart.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.features.cart.domain.entity.CartItem
import org.example.cross.card.features.cart.domain.repository.CartRepo

class GetAllItemsInCartUseCase(
    private val repo: CartRepo
) {
    suspend operator fun invoke(
    ): Flow<Result<List<CartItem>>> {
        return repo.getAllItemsInCart()
    }
}