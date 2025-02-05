package org.example.cross.card.product.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cross.card.product.domain.entity.Image

@Serializable
data class ImageResponse(
    val id: String,
    @SerialName("product_id") val productId: String?,
    @SerialName("image_url") val imageUrl: String?,
    @SerialName("is_main") val isMain: Boolean?
)

@Serializable
data class ThumbnailResponse(
    @SerialName("product_id") val productId: String?,
    @SerialName("url") val imageUrl: String?,
)

fun ImageResponse?.toDomain(): Image =
    if (this?.imageUrl == null) Image.None else Image.Url(imageUrl, isMain ?: false)

fun ThumbnailResponse?.toDomain(): Image =
    if (this?.imageUrl == null) Image.None else Image.Url(imageUrl, true)
