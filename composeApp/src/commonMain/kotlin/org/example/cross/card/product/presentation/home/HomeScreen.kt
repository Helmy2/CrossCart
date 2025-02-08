package org.example.cross.card.product.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import org.example.cross.card.product.domain.entity.Product
import org.example.cross.card.product.presentation.components.CategoryRow
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
    Column(modifier.imePadding().semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchInputField(
                    query = state.query,
                    expanded = state.expandedSearch,
                    onQueryChange = { onEvent(HomeEvent.UpdateQuery(it)) },
                    onSearch = { onEvent(HomeEvent.SearchProducts(it)) },
                    onExpandedChange = { onEvent(HomeEvent.UpdateExpandedSearch(it)) },
                    onBackClick = { onEvent(HomeEvent.UpdateExpandedSearch(false)) }
                )
            },
            expanded = state.expandedSearch,
            onExpandedChange = { onEvent(HomeEvent.UpdateExpandedSearch(it)) },
        ) {
            ProductGrid(
                products = state.searchProducts,
                onProductClick = onProductClick,
                loading = state.isLoading
            )
        }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            state.categories.forEach {
                CategoryRow(it, onClick = onProductClick)
            }
        }
    }
}


