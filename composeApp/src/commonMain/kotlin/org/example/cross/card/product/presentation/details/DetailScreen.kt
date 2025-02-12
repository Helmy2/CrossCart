package org.example.cross.card.product.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.cross.card.core.presentation.components.BackHandler
import org.example.cross.card.product.presentation.components.ProductDetails

@Composable
fun DetailScreen(
    state: DetailState,
    onEvent: (DetailEvent) -> Unit,
    productId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    BackHandler {
        onBackClick()
    }

    LaunchedEffect(productId) {
        onEvent(DetailEvent.LoadProduct(productId))
    }

    Box(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        ProductDetails(
            state.product,
            loading = state.isLoading,
            onFavoriteClick = {
                onEvent(DetailEvent.ToggleFavorite)
            }
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back"
            )
        }
    }
}

