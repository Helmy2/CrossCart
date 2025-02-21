package org.example.cross.card.features.checkout.domain.usecase

import org.example.cross.card.features.checkout.data.models.OrderResponse
import org.example.cross.card.features.checkout.domain.repository.CheckoutRepo

class CreateOrderUseCase(
    private val checkoutRepository: CheckoutRepo
) {
    suspend operator fun invoke(orderResponse: OrderResponse): Result<Unit> {
        return checkoutRepository.createOrder(orderResponse)
    }
}