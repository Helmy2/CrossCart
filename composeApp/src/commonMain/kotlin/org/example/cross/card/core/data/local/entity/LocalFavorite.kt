package org.example.cross.card.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class LocalFavorite(
    @PrimaryKey(autoGenerate = false)
    val productId: String
)