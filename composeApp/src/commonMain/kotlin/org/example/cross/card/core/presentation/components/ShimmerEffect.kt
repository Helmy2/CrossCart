package org.example.cross.card.core.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

fun Modifier.shimmerEffect(): Modifier = composed {
    val colors = listOf(
        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
    )
    val transition = rememberInfiniteTransition(label = "shimmer")
    val shimmerAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(animation = tween(1000, easing = LinearEasing)),
        label = "shimmer"
    )

    clip(MaterialTheme.shapes.medium).background(
        Brush.linearGradient(
            colors = colors,
            start = Offset.Zero,
            end = Offset(x = shimmerAnimation.value, y = shimmerAnimation.value * 2)
        )
    )
}