package org.example.cross.card.core.presentation.components

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.LocalPlatformContext
import coil3.request.crossfade

@Composable
fun imageLoader(context: PlatformContext = LocalPlatformContext.current): ImageLoader {
    return ImageLoader.Builder(context)
        .crossfade(true)
        .build()
}