package org.example.cross.card.details.domain.entity

import org.example.cross.card.core.domain.entity.Image

data class ProductDetails(
    val id: String,
    val title: String,
    val price: Float,
    val discount: Float,
    val description: String,
    val brand: String,
    val rating: Float,
    val stock: Int,
    val warranty: String,
    val shipping: String,
    val availability: String,
    val returnPolicy: String,
    val isFavorite: Boolean,
    val inCart: Boolean,
    val category: Category,
    val images: List<Image>,
)