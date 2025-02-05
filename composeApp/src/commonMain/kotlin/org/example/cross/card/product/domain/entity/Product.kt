package org.example.cross.card.product.domain.entity

data class Product(
    val id: String,
    val title: String,
    val rating: Double,
    val price: Double,
    val discountPercentage: Double,
    val image: Image
)