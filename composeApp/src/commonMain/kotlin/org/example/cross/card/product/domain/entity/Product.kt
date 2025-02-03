package org.example.cross.card.product.domain.entity

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val discount: Double,
    val popular: Boolean,
    val image: Image
)