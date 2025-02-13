package org.example.cross.card.product.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.example.cross.card.product.domain.entity.Order
import org.example.cross.card.product.domain.entity.OrderBy
import org.example.cross.card.product.domain.entity.Product
import org.example.cross.card.product.presentation.components.CategoryGrid
import org.example.cross.card.product.presentation.components.ProductGrid
import org.example.cross.card.product.presentation.components.SearchInputField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    val animatedPadding by animateDpAsState(
        targetValue = if (state.expandedSearch) 0.dp else 16.dp
    )
    Column(
        modifier.semantics { isTraversalGroup = true },
    ) {
        SearchBar(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(
                start = animatedPadding, end = animatedPadding, bottom = animatedPadding
            ).semantics { traversalIndex = 0f }.clip(MaterialTheme.shapes.large),
            inputField = {
                SearchInputField(
                    query = state.query,
                    expanded = state.expandedSearch,
                    onQueryChange = { onEvent(HomeEvent.UpdateQuery(it)) },
                    onSearch = { onEvent(HomeEvent.SearchProducts) },
                    onExpandedChange = { onEvent(HomeEvent.UpdateExpandedSearch(it)) },
                    onBackClick = { onEvent(HomeEvent.UpdateExpandedSearch(false)) },
                    onFilterClick = { onEvent(HomeEvent.UpdateFilterDialog(true)) },
                    modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth()
                )
            },
            expanded = state.expandedSearch,
            onExpandedChange = { onEvent(HomeEvent.UpdateExpandedSearch(it)) },
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                ProductGrid(
                    products = state.searchProducts,
                    onProductClick = onProductClick,
                    loading = state.loading
                )
                this@SearchBar.AnimatedVisibility(state.showFilterDialog) {
                    FilterDialog(
                        onDismiss = { onEvent(HomeEvent.UpdateFilterDialog(false)) },
                        filter = state.filter,
                        orderBy = state.orderBy,
                        onFilterChange = { onEvent(HomeEvent.UpdateFilter(it)) },
                        onOrderByChange = { onEvent(HomeEvent.UpdateOrderBy(it)) },
                        onConfirm = { onEvent(HomeEvent.SearchProducts) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }

        CategoryGrid(
            categories = state.categories, onProductClick = onProductClick, loading = state.loading
        )
    }
}

@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    onFilterChange: (Filter) -> Unit,
    onOrderByChange: (OrderBy) -> Unit,
    onConfirm: () -> Unit,
    filter: Filter,
    orderBy: OrderBy,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface {
            Column(
                modifier = modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OrderByItem(
                    orderBy = orderBy,
                    onOrderByChange = onOrderByChange,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                FilterItem(
                    filter = filter,
                    onFilterChange = onFilterChange,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(
                    onClick = {
                        onDismiss()
                        onConfirm()
                    },
                ) {
                    Text(text = "Apply")
                }
            }
        }
    }

}

@Composable
fun FilterItem(filter: Filter, onFilterChange: (Filter) -> Unit, modifier: Modifier) {
    Column(modifier) {
        Text(text = "Filter By", modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Rating")
            Slider(
                value = filter.rating ?: 0f,
                onValueChange = {
                    onFilterChange(filter.copy(rating = it))
                },
                valueRange = 0f..5f,
            )
        }
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "From Price")
            TextField(
                value = filter.fromPrice?.toString() ?: "",
                onValueChange = {
                    onFilterChange(filter.copy(fromPrice = it.toFloatOrNull()))
                },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
        }
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "To Price")
            TextField(
                value = filter.toPrice?.toString() ?: "",
                onValueChange = {
                    onFilterChange(filter.copy(toPrice = it.toFloatOrNull()))
                },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
        }
    }
}

@Composable
fun OrderByItem(orderBy: OrderBy, onOrderByChange: (OrderBy) -> Unit, modifier: Modifier) {
    var orderByDropdownVisible by remember { mutableStateOf(false) }
    var sortDropdownVisible by remember { mutableStateOf(false) }
    Column(modifier) {
        Text(text = "Order By", modifier = Modifier.padding(8.dp))

        Box {
            DropdownMenuItem(text = { Text(text = orderBy.name) }, onClick = {
                orderByDropdownVisible = true
            }, modifier = Modifier.padding(8.dp).clip(MaterialTheme.shapes.medium)
            )
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
            DropdownMenuItem(text = { Text(text = orderBy.order.toString()) }, onClick = {
                sortDropdownVisible = true
            }, modifier = Modifier.padding(8.dp).clip(MaterialTheme.shapes.medium)
            )
            DropdownMenu(
                expanded = sortDropdownVisible,
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






