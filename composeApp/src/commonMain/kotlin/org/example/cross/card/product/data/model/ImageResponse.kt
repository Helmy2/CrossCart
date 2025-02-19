package org.example.cross.card.product.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cross.card.core.domain.entity.Image

@Serializable
data class ImageResponse(
    val id: String,
    @SerialName("product_id") val productId: String?,
    @SerialName("url") val imageUrl: String?,
)

@Serializable
data class ThumbnailResponse(
    @SerialName("product_id") val productId: String?,
    @SerialName("url") val imageUrl: String?,
)

fun ImageResponse?.toDomain(): Image =
    if (this?.imageUrl == null) Image.None else Image.Url(imageUrl, false)

fun ThumbnailResponse?.toDomain(): Image =
    if (this?.imageUrl == null) Image.None else Image.Url(imageUrl, true)
