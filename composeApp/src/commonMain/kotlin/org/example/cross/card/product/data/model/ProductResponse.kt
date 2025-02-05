package org.example.cross.card.product.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cross.card.product.domain.entity.Product

@Serializable
data class ProductResponse(
    val id: String,
    val title: String?,
    val price: Double?,
    val rating: Double?,
    @SerialName("discount_percentage")
    val discountPercentage: Double?,
    @SerialName("category_id") val categoryId: String?
)

fun ProductResponse.toDomain(
    image: ThumbnailResponse?
) = Product(
    id = id,
    title = title ?: "Empty",
    price = price ?: 0.0,
    rating = rating ?: 0.0,
    discountPercentage = discountPercentage ?: 0.0,
    image = image.toDomain()
)