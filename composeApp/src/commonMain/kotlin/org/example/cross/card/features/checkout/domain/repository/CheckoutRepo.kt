package org.example.cross.card.features.checkout.domain.repository

import org.example.cross.card.features.checkout.data.models.OrderItem
import org.example.cross.card.features.checkout.domain.models.Address

interface CheckoutRepo {
    suspend fun createOrder(orderItems: List<OrderItem>, shippingAddress: Address): Result<Unit>
}
