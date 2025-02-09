package org.example.cross.card.product.presentation.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    val animatedPadding by animateDpAsState(
        targetValue = if (state.expandedSearch) 16.dp else 0.dp,
        label = "padding"
    )

    Column(
        modifier
            .imePadding()
            .animateContentSize()
            .semantics { isTraversalGroup = true },
    ) {
        SearchBar(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp, vertical = animatedPadding)
                .semantics { traversalIndex = 0f }
                .clip(MaterialTheme.shapes.large),
            inputField = {
                SearchInputField(
                    query = state.query,
                    expanded = state.expandedSearch,
                    onQueryChange = { onEvent(HomeEvent.UpdateQuery(it)) },
                    onSearch = { onEvent(HomeEvent.SearchProducts(it)) },
                    onExpandedChange = { onEvent(HomeEvent.UpdateExpandedSearch(it)) },
                    onBackClick = { onEvent(HomeEvent.UpdateExpandedSearch(false)) },
                    modifier = Modifier.sizeIn(maxWidth = 600.dp)
                        .fillMaxWidth()
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


