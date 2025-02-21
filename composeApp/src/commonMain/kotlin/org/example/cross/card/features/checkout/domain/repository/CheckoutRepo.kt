package org.example.cross.card.features.checkout.domain.repository

import org.example.cross.card.features.checkout.data.models.OrderResponse

interface CheckoutRepo {
    suspend fun createOrder(orderResponse: OrderResponse): Result<Unit>
}
