package org.example.cross.card.features.checkout.data.models

import kotlinx.serialization.SerialName
import org.example.cross.card.features.checkout.domain.models.Address
import org.example.cross.card.features.checkout.domain.models.OrderStatus

data class OrderResponse(
    @SerialName("user_id")
    val userId: String,
    @SerialName("items_id")
    val itemsId: List<String>,
    val total: Float,
    @SerialName("shipping_address")
    val shippingAddress: Address,
    val status: OrderStatus = OrderStatus.PROCESSING,
)

