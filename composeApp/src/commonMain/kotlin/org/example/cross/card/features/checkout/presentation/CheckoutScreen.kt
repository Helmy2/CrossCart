package org.example.cross.card.features.checkout.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.cross.card.features.checkout.presentation.components.AddressDialog


@Composable
fun CheckoutScreen(
    state: CheckoutState,
    onEvent: (CheckoutEvent) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        if (state.loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Checkout",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
                LazyColumn {
                    items(state.items) {
                        Row {
                            Text(
                                text = "${it.product.title} x ${it.quantity}",
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Total: ${it.product.price * it.quantity}",
                            )
                        }

                    }
                }
                Row {
                    Text("Total")
                    Spacer(modifier = Modifier.weight(1f))
                    Text("${state.total}")
                }
                AnimatedVisibility(state.shippingAddress != null) {
                    Row {
                        Text("Shipping Address: ")
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${state.shippingAddress?.street}, ${state.shippingAddress?.city}, ${state.shippingAddress?.state}, ${state.shippingAddress?.country}",
                        )
                    }
                }
                TextButton(
                    onClick = { onEvent(CheckoutEvent.UpdateAddressDialog(true)) },
                ) {
                    Text(if (state.shippingAddress != null) "Change Address" else "Add Address")
                }
                TextButton(
                    onClick = { onEvent(CheckoutEvent.Checkout) },
                    enabled = state.validOrder
                ) {
                    Text("Checkout")
                }
            }
        }
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart).padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
            )
        }
        AnimatedVisibility(state.showAddressDialog) {
            AddressDialog(
                state.shippingAddress,
                onDismiss = { onEvent(CheckoutEvent.UpdateAddressDialog(false)) },
                onConfirm = { onEvent(CheckoutEvent.UpdateShippingAddress(it)) },
            )
        }
    }
}

