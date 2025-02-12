package org.example.cross.card.product.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import org.example.cross.card.core.presentation.components.imageLoader
import org.example.cross.card.core.presentation.components.shimmerEffect
import org.example.cross.card.product.domain.entity.ProductDetails

@Composable
fun ProductDetails(
    product: ProductDetails?,
    loading: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit
) {
    val context = LocalPlatformContext.current

    if (product == null || loading) {
        ProductDetailsShimmer()
    } else {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box {
                if (product.images.size == 1) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = product.images.first().url,
                            imageLoader = imageLoader(context),
                        ),
                        contentDescription = "Product Image",
                        modifier = Modifier.fillMaxWidth()
                            .height(300.dp)
                            .clip(
                                MaterialTheme.shapes.large.copy(
                                    topEnd = CornerSize(0),
                                    topStart = CornerSize(0)
                                )
                            )
                            .background(MaterialTheme.colorScheme.secondary.copy(0.5f))
                    )
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(product.images) { image ->
                            Card {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = image.url,
                                        imageLoader = imageLoader(context),
                                    ),
                                    contentDescription = "Product Image",
                                    modifier = Modifier.size(300.dp)
                                        .background(MaterialTheme.colorScheme.secondary.copy(0.5f)),
                                )
                            }
                        }
                    }
                }
                FavoriteButton(
                    isFavorite = product.isFavorite,
                    onClick = onFavoriteClick,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
                )
            }

            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Price: $${product.price}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                if (product.discount > 0) {
                    Text(
                        text = "Discount: ${product.discount}%",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Brand: ${product.brand}")
                Text(text = "Category: ${product.category.name}")
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Rating: ${product.rating}")
                Text(text = "Stock: ${product.stock}")
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(text = product.description)
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Additional Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Warranty: ${product.warranty}")
                Text(text = "Shipping: ${product.shipping}")
                Text(text = "Availability: ${product.availability}")
                Text(text = "Return Policy: ${product.returnPolicy}")

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProductDetailsShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .shimmerEffect()
        )

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .shimmerEffect()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 100.dp, height = 24.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 20.dp)
                        .shimmerEffect()
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 20.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 20.dp)
                        .shimmerEffect()
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 20.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 20.dp)
                        .shimmerEffect()
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .shimmerEffect()
            )
        }
    }
}