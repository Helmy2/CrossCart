package org.example.cross.card.product.presentation.components

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pinch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun ZoomableContent(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    var enabled by remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset = Offset(offset.x + offsetChange.x, offset.y + offsetChange.y)
    }

    Box(
        modifier = modifier
    ) {
        Box(
            Modifier.graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offset.x,
                translationY = offset.y
            ).transformable(state, enabled = enabled)
        ) {
            content.invoke()
        }
        IconButton(
            onClick = {
                scale = 1f
                offset = Offset.Zero
                enabled = !enabled
            },
            modifier = Modifier.align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Pinch,
                contentDescription = "Zoom",
                tint = if (enabled) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.size(50.dp)
            )
        }
    }
}