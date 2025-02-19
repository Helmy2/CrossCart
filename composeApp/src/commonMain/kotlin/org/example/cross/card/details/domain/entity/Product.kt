package org.example.cross.card.details.domain.entity

import org.example.cross.card.core.domain.entity.Image

data class Product(
    val id: String,
    val categoryId: String,
    val title: String,
    val rating: Float,
    val price: Float,
    val discountPercentage: Float,
    val image: Image
)