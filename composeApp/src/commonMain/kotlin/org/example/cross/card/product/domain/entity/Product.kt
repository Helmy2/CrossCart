package org.example.cross.card.product.domain.entity

data class Product(
    val id: String,
    val categoryId: String,
    val title: String,
    val rating: Float,
    val price: Float,
    val discountPercentage: Float,
    val image: Image
)

data class CartItem(
    val product: Product,
    val quantity: Int,
)