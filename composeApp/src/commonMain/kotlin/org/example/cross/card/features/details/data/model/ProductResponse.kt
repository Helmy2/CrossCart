package org.example.cross.card.features.details.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cross.card.core.data.local.entity.LocalProduct
import org.example.cross.card.features.details.domain.entity.Product

@Serializable
data class ProductResponse(
    val id: String,
    val title: String?,
    val price: Float?,
    val rating: Float?,
    @SerialName("discount_percentage")
    val discountPercentage: Float?,
    @SerialName("category_id") val categoryId: String
)

fun ProductResponse.toLocal(
    image: ThumbnailResponse?,
    categoryName: String?
) = LocalProduct(
    id = id,
    title = title ?: "Empty",
    price = price ?: 0f,
    rating = rating ?: 0f,
    discountPercentage = discountPercentage ?: 0f,
    categoryName = categoryName,
    imageUrl = image?.imageUrl
)


fun ProductResponse.toDomain(
    image: ThumbnailResponse?
) = Product(
    id = id,
    categoryName = categoryId,
    title = title ?: "Empty",
    price = price ?: 0f,
    rating = rating ?: 0f,
    discountPercentage = discountPercentage ?: 0f,
    image = image.toDomain()
)