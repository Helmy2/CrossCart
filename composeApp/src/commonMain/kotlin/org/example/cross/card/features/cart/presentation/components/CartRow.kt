package org.example.cross.card.features.cart.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.cross.card.core.presentation.components.shimmerEffect
import org.example.cross.card.features.cart.domain.entity.CartItem

@Composable
fun CartRow(
    products: List<CartItem>,
    syncing: Boolean,
    onProductClick: (CartItem) -> Unit,
    onDelete: (CartItem) -> Unit,
    onIncrease: (CartItem) -> Unit,
    onDecrease: (CartItem) -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        if (products.isEmpty() && !loading)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                ) {
                    Text(
                        "Cart is empty",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        items(
            if (loading) 5 else 0
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().height(100.dp).padding(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().shimmerEffect())
            }
        }
        items(products) {
            ProductCartItem(
                cartItem = it,
                syncing = syncing,
                onClick = { onProductClick(it) },
                onDelete = { onDelete(it) },
                onIncrease = { onIncrease(it) },
                onDecrease = { onDecrease(it) },
                modifier = Modifier.fillMaxWidth().height(120.dp).padding(8.dp),
            )
        }
    }
}