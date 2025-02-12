package org.example.cross.card.product.domain.entity

data class ProductDetails(
    val id: String,
    val title: String,
    val price: Double,
    val discount: Double,
    val description: String,
    val brand: String,
    val rating: Double,
    val stock: Int,
    val warranty: String,
    val shipping: String,
    val availability: String,
    val returnPolicy: String,
    val minimumOrder: Int,
    val isFavorite: Boolean,
    val category: Category,
    val images: List<Image>,
)