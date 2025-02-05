package org.example.cross.card.product.data.model

import kotlinx.serialization.Serializable
import org.example.cross.card.product.domain.entity.Category

@Serializable
data class CategoryResponse(
    val id: String, val name: String?,
)

fun CategoryResponse.toDomain() = Category(
    id = id,
    name = name ?: "Empty",
)
