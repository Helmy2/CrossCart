package org.example.cross.card.cart.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import org.example.cross.card.cart.presentation.components.CartRow
import org.example.cross.card.details.domain.entity.Product


@Composable
fun CartScreen(
    state: CartState,
    handleEvent: (CartEvent) -> Unit,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .animateContentSize()
            .semantics { isTraversalGroup = true }
            .padding(16.dp),
    ) {
        CartRow(
            products = state.products,
            onProductClick = { onProductClick(it.product) },
            loading = state.loading,
            onDelete = { handleEvent(CartEvent.RemoveFromCart(it)) },
            onIncrease = { handleEvent(CartEvent.IncreaseQuantity(it)) },
            onDecrease = { handleEvent(CartEvent.DecreaseQuantity(it)) },
            syncing = state.syncing,
            modifier = Modifier.weight(1f)
        )
        Row(modifier) {
            Text(
                "Total Price: ",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                "${state.totalPrice}$",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.weight(1f))
            if (state.products.isNotEmpty()) {
                TextButton(onClick = { handleEvent(CartEvent.ClearAll) }) {
                    Text("Clear Cart")
                }
            }
        }
    }
}


