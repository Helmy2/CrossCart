package org.example.cross.card.product.domain.entity

data class ProductDetails(
    val id: String,
    val title: String,
    val price: Double,
    val discount: Double,
    val popular: Boolean,
    val description: String,
    val brand: String,
    val model: String,
    val category: Category,
    val images: List<Image>
)