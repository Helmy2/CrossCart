package org.example.cross.card.product.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cross.card.product.domain.entity.ProductDetails

@Serializable
data class ProductDetailsResponse(
    val id: String,
    val title: String?,
    val price: Double?,
    val discount: Double?,
    val popular: Boolean?,
    val description: String?,
    val brand: String?,
    val model: String?,
    @SerialName("categories") val category: CategoryResponse
)

fun ProductDetailsResponse.toDomain(
    list: List<ImageResponse>
) = ProductDetails(
    id = id,
    title = title ?: "Empty",
    price = price ?: 0.0,
    discount = discount ?: 0.0,
    popular = popular ?: false,
    description = description ?: "Empty",
    brand = brand ?: "Empty",
    model = model ?: "Empty",
    category = category.toDomain(),
    images = list.map { it.toDomain() }
)