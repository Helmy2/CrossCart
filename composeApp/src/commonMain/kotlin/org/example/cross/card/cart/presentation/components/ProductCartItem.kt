package org.example.cross.card.cart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.ic_placeholder
import org.example.cross.card.cart.domain.entity.CartItem
import org.example.cross.card.core.presentation.components.imageLoader
import org.example.cross.card.core.util.format
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

@Composable
fun ProductCartItem(
    cartItem: CartItem,
    syncing: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                SubcomposeAsyncImage(
                    model = cartItem.product.image.url,
                    contentDescription = null,
                    imageLoader = imageLoader(),
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary.copy(0.5f))
                        .fillMaxHeight().aspectRatio(1f),
                    error = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_placeholder),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                    },
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp).align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        cartItem.product.rating.format(1),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Column(modifier = Modifier.padding(8.dp).weight(1f)) {
                Text(
                    cartItem.product.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text("${cartItem.product.price}$")
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${(cartItem.product.price * (1 + cartItem.product.discountPercentage / 100)).roundToInt()}$",
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "${(cartItem.product.discountPercentage.format(1))}%Off",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                    modifier = Modifier.padding(4.dp).clip(CircleShape).clickable(
                        enabled = !syncing
                    ) {
                        onIncrease()
                    }
                )
                Text(
                    cartItem.quantity.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(4.dp)
                )
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Decrease",
                    modifier = Modifier.padding(4.dp).clip(CircleShape).clickable(
                        enabled = (cartItem.quantity > 1) && !syncing
                    ) {
                        onDecrease()
                    }
                )
            }
            IconButton(onClick = onDelete, enabled = !syncing) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "ShoppingCart",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}