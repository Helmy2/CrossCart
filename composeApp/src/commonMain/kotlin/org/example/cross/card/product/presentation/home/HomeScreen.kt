package org.example.cross.card.product.presentation.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.ic_placeholder
import org.example.cross.card.core.presentation.components.imageLoader
import org.example.cross.card.product.domain.entity.CategoryWithProducts
import org.example.cross.card.product.domain.entity.Product
import org.jetbrains.compose.resources.painterResource


@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)
            .imePadding()
    ) {
        state.categories.forEach {
            CategoryRow(it)
        }
    }
}

@Composable
fun CategoryRow(
    category: CategoryWithProducts,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(category.category.name)
        LazyRow {
            items(if (category.products.isEmpty()) 5 else 0) {
                ProductItemShimmer(modifier.height(300.dp))
            }
            items(category.products, key = { it.id }) {
                ProductItem(it, onClick = {}, modifier.height(300.dp))
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
        modifier = modifier.aspectRatio(.7f).padding(8.dp),
        onClick = onClick,

        ) {
        SubcomposeAsyncImage(model = product.image.url,
            contentDescription = null,
            imageLoader = imageLoader(context),
            modifier = Modifier.weight(1f).fillMaxWidth(),
            loading = {
                Icon(
                    painter = painterResource(Res.drawable.ic_placeholder),
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp).shimmerEffect()
                )
            },
            error = {
                Icon(
                    painter = painterResource(Res.drawable.ic_placeholder),
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp)
                )
            })
        Text(
            product.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )
        Text(product.price.toString())
        Text(product.discount.toString())
        Text(product.popular.toString())
    }
}

@Composable
fun ProductItemShimmer(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.aspectRatio(.7f).padding(8.dp),
    ) {
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth().shimmerEffect(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(30.dp).shimmerEffect()
        )
    }
}


fun Modifier.shimmerEffect(): Modifier = composed {
    val colors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )
    val transition = rememberInfiniteTransition(label = "shimmer")
    val shimmerAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(animation = tween(1000, easing = LinearEasing)),
        label = "shimmer"
    )


    background(
        Brush.linearGradient(
            colors = colors,
            start = Offset.Zero,
            end = Offset(x = shimmerAnimation.value, y = shimmerAnimation.value * 2)
        )
    )
}
