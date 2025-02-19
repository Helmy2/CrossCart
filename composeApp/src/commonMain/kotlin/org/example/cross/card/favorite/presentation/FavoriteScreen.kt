package org.example.cross.card.favorite.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import org.example.cross.card.product.domain.entity.Product
import org.example.cross.card.product.presentation.components.ProductGrid


@Composable
fun FavoriteScreen(
    state: FavoriteState,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .animateContentSize()
            .semantics { isTraversalGroup = true },
    ) {
        ProductGrid(
            products = state.products,
            onProductClick = onProductClick,
            loading = state.loading
        )
    }
}


