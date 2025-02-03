package org.example.cross.card.product.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cross.card.product.domain.entity.Product

@Serializable
data class ProductResponse(
    val id: String,
    val title: String?,
    val price: Double?,
    val discount: Double?,
    val popular: Boolean?,
    @SerialName("category_id") val categoryId: String?
)

fun ProductResponse.toDomain(
    image: ImageResponse?
) = Product(
    id = id,
    title = title ?: "Empty",
    price = price ?: 0.0,
    discount = discount ?: 0.0,
    popular = popular ?: false,
    image = image.toDomain()
)