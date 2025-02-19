package org.example.cross.card.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.cross.card.home.domain.entity.Order
import org.example.cross.card.home.domain.entity.OrderBy

@Composable
fun SortingSection(
    orderBy: OrderBy, onOrderByChange: (OrderBy) -> Unit, modifier: Modifier = Modifier
) {
    var orderByDropdownVisible by remember { mutableStateOf(false) }
    var sortDropdownVisible by remember { mutableStateOf(false) }
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Order By", modifier = Modifier.padding(8.dp))

        Box {
            Card(onClick = { orderByDropdownVisible = true }) {
                Text(text = orderBy.name, modifier = Modifier.padding(8.dp))
            }
            DropdownMenu(expanded = orderByDropdownVisible,
                onDismissRequest = { orderByDropdownVisible = false }) {
                OrderBy.all.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            onOrderByChange(it)
                            orderByDropdownVisible = false
                        },
                    )
                }
            }
        }
        Box {
            Card(onClick = { sortDropdownVisible = true }) {
                Text(text = orderBy.order.toString(), modifier = Modifier.padding(8.dp))
            }
            DropdownMenu(expanded = sortDropdownVisible,
                onDismissRequest = { sortDropdownVisible = false }) {

                DropdownMenuItem(
                    text = { Text(text = "Ascending") },
                    onClick = {
                        onOrderByChange(orderBy.copy(order = Order.Ascending))
                        sortDropdownVisible = false
                    },
                )
                DropdownMenuItem(
                    text = { Text(text = "Descending") },
                    onClick = {
                        onOrderByChange(orderBy.copy(order = Order.Descending))
                        sortDropdownVisible = false
                    },
                )
            }
        }
    }
}