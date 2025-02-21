package org.example.cross.card.features.checkout.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val userId: String,
    val itemsId: List<String>,
    val total: Float,
    val shippingAddress: Address,
    val status: OrderStatus = OrderStatus.PROCESSING,
)