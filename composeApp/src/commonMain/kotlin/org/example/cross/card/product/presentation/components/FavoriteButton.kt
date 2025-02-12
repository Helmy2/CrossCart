package org.example.cross.card.product.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteButton(isFavorite: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick, modifier) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Favorite",
            tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(48.dp)
        )
    }
}