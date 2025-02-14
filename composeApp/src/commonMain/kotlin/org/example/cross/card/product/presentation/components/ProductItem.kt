package org.example.cross.card.product.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.ic_placeholder
import org.example.cross.card.core.presentation.components.imageLoader
import org.example.cross.card.core.util.format
import org.example.cross.card.product.domain.entity.Product
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

@Composable
fun ProductItem(
    product: Product, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    val context = LocalPlatformContext.current
    Card(
        modifier = modifier,
        onClick = onClick,
    ) {
        SubcomposeAsyncImage(
            model = product.image.url,
            contentDescription = null,
            imageLoader = imageLoader(context),
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary.copy(0.5f))
                .weight(1f).fillMaxWidth(),
            error = {
                Icon(
                    painter = painterResource(Res.drawable.ic_placeholder),
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp)
                )
            },
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    product.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "%.1f".format(product.rating),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Text("${product.price}$")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${(product.price * (1 + product.discountPercentage / 100)).roundToInt()}$",
                    textDecoration = TextDecoration.LineThrough,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "${"%.1f".format(product.discountPercentage)}%Off",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}