package org.example.cross.card.product.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.ic_placeholder
import org.example.cross.card.core.presentation.components.imageLoader
import org.example.cross.card.core.presentation.components.shimmerEffect
import org.example.cross.card.product.domain.entity.CategoryWithProducts
import org.example.cross.card.product.domain.entity.Product
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

@Composable
fun CategoryRow(
    category: CategoryWithProducts,
    onClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            category.category.name,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
        LazyRow {
            items(if (category.products.isEmpty()) 5 else 0) {
                Card(Modifier.height(300.dp).aspectRatio(.7f).padding(8.dp)) {
                    Box(Modifier.fillMaxSize().shimmerEffect())
                }
            }
            items(category.products, key = { it.id }) {
                ProductItem(
                    it,
                    onClick = { onClick(it) },
                    Modifier.height(300.dp).aspectRatio(.7f).padding(8.dp)
                )
            }
        }
    }
}

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
            Text(
                product.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text("${product.price}$")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${(product.price * (1 + product.discountPercentage / 100)).roundToInt()}$",
                    textDecoration = TextDecoration.LineThrough,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "${product.discountPercentage.roundToInt()}%Off",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}