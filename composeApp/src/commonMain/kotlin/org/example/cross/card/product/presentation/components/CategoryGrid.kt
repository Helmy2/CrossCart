package org.example.cross.card.product.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.cross.card.cart.domain.entity.CategoryWithProducts
import org.example.cross.card.core.presentation.components.shimmerEffect
import org.example.cross.card.product.domain.entity.Product

@Composable
fun CategoryGrid(
    categories: List<CategoryWithProducts>,
    onProductClick: (Product) -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 200.dp),
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        if (loading) {
            items(5) {
                Card(
                    Modifier
                        .height(300.dp)
                        .padding(8.dp)
                ) {
                    Box(Modifier.fillMaxSize().shimmerEffect())
                }
            }
        } else {
            categories.forEach { categories ->
                item(
                    span = StaggeredGridItemSpan.FullLine
                ) {
                    Text(
                        categories.category.name,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                items(if (categories.products.isEmpty()) 5 else 0) {
                    Card(
                        Modifier
                            .height(300.dp).padding(8.dp)
                    ) {
                        Box(Modifier.fillMaxSize().shimmerEffect())
                    }
                }
                items(categories.products, key = { it.id }) {
                    ProductItem(
                        it,
                        onClick = { onProductClick(it) },
                        Modifier.height(300.dp)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}