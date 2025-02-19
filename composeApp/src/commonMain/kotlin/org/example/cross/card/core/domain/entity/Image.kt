package org.example.cross.card.core.domain.entity

sealed class Image {
    val url: String?
        get() = when (this) {
            is Url -> imageUrl
            is None -> null
        }

    data object None : Image()
    data class Url(val imageUrl: String, val isMain: Boolean) : Image()
}