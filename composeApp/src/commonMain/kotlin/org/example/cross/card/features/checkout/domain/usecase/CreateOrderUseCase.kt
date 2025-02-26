package org.example.cross.card.features.checkout.domain.usecase

import org.example.cross.card.features.checkout.data.models.OrderItem
import org.example.cross.card.features.checkout.domain.models.Address
import org.example.cross.card.features.checkout.domain.repository.CheckoutRepo

class CreateOrderUseCase(
    private val checkoutRepository: CheckoutRepo
) {
    suspend operator fun invoke(
        orderItems: List<OrderItem>,
        shippingAddress: Address
    ): Result<Unit> {
        return checkoutRepository.createOrder(orderItems, shippingAddress)
    }
}