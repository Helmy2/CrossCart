package org.example.cross.card.product.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import org.example.cross.card.details.domain.entity.Product
import org.example.cross.card.details.presentation.components.ProductGrid
import org.example.cross.card.product.presentation.components.CategoryGrid
import org.example.cross.card.product.presentation.components.FilterDialog
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






