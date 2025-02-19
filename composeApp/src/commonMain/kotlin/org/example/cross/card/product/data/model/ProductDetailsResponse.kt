package org.example.cross.card.product.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cross.card.product.domain.entity.ProductDetails


@Serializable
data class ProductDetailsResponse(
    val id: String,
    val title: String?,
    val price: Float?,
    @SerialName("discount_percentage")
    val discount: Float?,
    val description: String?,
    val rating: Float?,
    val stock: Int?,
    val brand: String?,
    @SerialName("warranty_information") val warranty: String?,
    @SerialName("shipping_information") val shipping: String?,
    @SerialName("availability_status") val availability: String?,
    @SerialName("return_policy") val returnPolicy: String?,
    @SerialName("categories") val category: CategoryResponse
)

fun ProductDetailsResponse.toDomain(
    imageUrls: List<ImageResponse>,
    isFavorite: Boolean,
    inCart: Boolean
) = ProductDetails(
    id = id,
    title = title ?: "Empty",
    price = price ?: 0f,
    discount = discount ?: 0f,
    description = description ?: "Empty",
    brand = brand ?: "Empty",
    category = category.toDomain(),
    images = imageUrls.map { it.toDomain() },
    rating = rating ?: 0f,
    stock = stock ?: 0,
    warranty = warranty ?: "Empty",
    shipping = shipping ?: "Empty",
    availability = availability ?: "Empty",
    returnPolicy = returnPolicy ?: "Empty",
    isFavorite = isFavorite,
    inCart = inCart
)