package org.example.cross.card.features.checkout.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cross.card.features.checkout.domain.models.Address
import org.example.cross.card.features.checkout.domain.models.OrderStatus

@Serializable
data class OrderResponse(
    @SerialName("user_id")
    val userId: String,
    @SerialName("order_id")
    val orderItems: List<OrderItem>,
    @SerialName("shipping_address")
    val shippingAddress: Address,
    val status: OrderStatus = OrderStatus.PROCESSING,
)

