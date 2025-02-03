package org.example.cross.card.product.data.model

import kotlinx.serialization.Serializable
import org.example.cross.card.product.domain.entity.Category
import org.example.cross.card.product.domain.entity.Image

@Serializable
data class CategoryResponse(
    val id: String, val name: String?, val image: String?
)

fun CategoryResponse.toDomain() = Category(
    id = id,
    name = name ?: "Empty",
    image = if (image == null) Image.None else Image.Url(image, true)
)
