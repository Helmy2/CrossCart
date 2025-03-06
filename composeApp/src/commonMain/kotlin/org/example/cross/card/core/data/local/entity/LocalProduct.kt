package org.example.cross.card.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.cross.card.core.domain.entity.Image
import org.example.cross.card.features.details.domain.entity.Product


@Entity
data class LocalProduct(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String?,
    val price: Float?,
    val rating: Float?,
    val discountPercentage: Float?,
    val categoryName: String?,
    val imageUrl: String?
)


fun LocalProduct.toDomain(): Product = Product(
    id = id,
    title = title ?: "",
    price = price ?: 0f,
    rating = rating ?: 0f,
    discountPercentage = discountPercentage ?: 0f,
    categoryName = categoryName ?: "",
    image = if (imageUrl == null) Image.None else Image.Url(imageUrl, true)
)