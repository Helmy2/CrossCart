package org.example.cross.card.product.domain.entity

sealed class Image {
    data object None : Image()
    data class Url(val imageUrl: String, val isMain: Boolean) : Image()
}