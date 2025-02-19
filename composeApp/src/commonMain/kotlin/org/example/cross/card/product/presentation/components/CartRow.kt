package org.example.cross.card.product.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.cross.card.core.presentation.components.shimmerEffect
import org.example.cross.card.product.domain.entity.Product

@Composable
fun CartRow(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(
            if (products.isEmpty() || loading) 5 else 0
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().height(100.dp).padding(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().shimmerEffect())
            }
        }
        items(products) {
            ProductCartItem(
                product = it,
                onClick = { onProductClick(it) },
                modifier = Modifier.fillMaxWidth().height(100.dp).padding(8.dp),
            )
        }
    }
}