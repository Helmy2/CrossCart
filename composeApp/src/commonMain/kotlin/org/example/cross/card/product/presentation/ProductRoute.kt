package org.example.cross.card.product.presentation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.product.presentation.cart.CartScreen
import org.example.cross.card.product.presentation.cart.CartViewModel
import org.example.cross.card.product.presentation.details.DetailScreen
import org.example.cross.card.product.presentation.details.DetailViewModel
import org.example.cross.card.product.presentation.favorite.FavoriteScreen
import org.example.cross.card.product.presentation.favorite.FavoriteViewModel
import org.example.cross.card.product.presentation.home.HomeScreen
import org.example.cross.card.product.presentation.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun NavGraphBuilder.productRoute() {
    composable<Destination.Main.Products> {
        val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>()
        ListDetailPaneScaffold(directive = scaffoldNavigator.scaffoldDirective,
            value = scaffoldNavigator.scaffoldValue,
            listPane = {
                val viewModel: HomeViewModel = koinViewModel()
                val state = viewModel.state.collectAsStateWithLifecycle()
                HomeScreen(
                    state = state.value, onEvent = viewModel::onEvent,
                    onProductClick = {
                        scaffoldNavigator.navigateTo(
                            ListDetailPaneScaffoldRole.Detail, it.id
                        )
                    },
                    modifier = Modifier.systemBarsPadding(),
                )
            },
            detailPane = {
                val viewModel: DetailViewModel = koinViewModel()
                val state = viewModel.state.collectAsStateWithLifecycle()
                scaffoldNavigator.currentDestination?.content?.let {
                    DetailScreen(
                        state = state.value,
                        onEvent = viewModel::handleEvent,
                        productId = it,
                        onBackClick = {
                            scaffoldNavigator.navigateBack()
                        },
                    )
                }
            })
    }

    composable<Destination.Main.Favorites> {
        val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>()
        ListDetailPaneScaffold(directive = scaffoldNavigator.scaffoldDirective,
            value = scaffoldNavigator.scaffoldValue,
            listPane = {
                val viewModel: FavoriteViewModel = koinViewModel()
                val state = viewModel.state.collectAsStateWithLifecycle()
                FavoriteScreen(
                    state = state.value,
                    onProductClick = {
                        scaffoldNavigator.navigateTo(
                            ListDetailPaneScaffoldRole.Detail, it.id
                        )
                    },
                    modifier = Modifier.systemBarsPadding(),
                )
            },
            detailPane = {
                val viewModel: DetailViewModel = koinViewModel()
                val state = viewModel.state.collectAsStateWithLifecycle()
                scaffoldNavigator.currentDestination?.content?.let {
                    DetailScreen(
                        state = state.value,
                        onEvent = viewModel::handleEvent,
                        productId = it,
                        onBackClick = {
                            scaffoldNavigator.navigateBack()
                        },
                    )
                }
            })
    }

    composable<Destination.Main.Cart> {
        val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>()
        ListDetailPaneScaffold(directive = scaffoldNavigator.scaffoldDirective,
            value = scaffoldNavigator.scaffoldValue,
            listPane = {
                val viewModel: CartViewModel = koinViewModel()
                val state = viewModel.state.collectAsStateWithLifecycle()
                CartScreen(
                    state = state.value,
                    onProductClick = {
                        scaffoldNavigator.navigateTo(
                            ListDetailPaneScaffoldRole.Detail, it.id
                        )
                    },
                    handleEvent = viewModel::handleEvent,
                    modifier = Modifier.systemBarsPadding(),
                )
            },
            detailPane = {
                val viewModel: DetailViewModel = koinViewModel()
                val state = viewModel.state.collectAsStateWithLifecycle()
                scaffoldNavigator.currentDestination?.content?.let {
                    DetailScreen(
                        state = state.value,
                        onEvent = viewModel::handleEvent,
                        productId = it,
                        onBackClick = {
                            scaffoldNavigator.navigateBack()
                        },
                    )
                }
            }
        )
    }
}
