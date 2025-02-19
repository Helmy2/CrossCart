package org.example.cross.card.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.cart.domain.usecase.AddToCartUseCase
import org.example.cross.card.cart.domain.usecase.ClearCartUseCase
import org.example.cross.card.cart.domain.usecase.GetAllItemsInCartUseCase
import org.example.cross.card.cart.domain.usecase.RemoveFromCartUseCase
import org.example.cross.card.cart.domain.usecase.UpdateCartQuantityUseCase
import org.example.cross.card.cart.presentation.CartViewModel
import org.example.cross.card.product.data.repository.ProductRepoImpl
import org.example.cross.card.product.domain.repository.ProductRepo
import org.example.cross.card.product.domain.usecase.AddToFavoriteUseCase
import org.example.cross.card.product.domain.usecase.GetCategoriesWithProductsUseCase
import org.example.cross.card.product.domain.usecase.GetFavoritesUseCase
import org.example.cross.card.product.domain.usecase.GetProductByIdUseCase
import org.example.cross.card.product.domain.usecase.GetProductsByNameUseCase
import org.example.cross.card.product.domain.usecase.RemoveFromFavoriteUseCase
import org.example.cross.card.product.presentation.details.DetailViewModel
import org.example.cross.card.product.presentation.favorite.FavoriteViewModel
import org.example.cross.card.product.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val productModule = module {
    single<ProductRepo> {
        ProductRepoImpl(
            supabase = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { GetCategoriesWithProductsUseCase(get()) }
    factory { GetProductByIdUseCase(get()) }
    factory { GetProductsByNameUseCase(get()) }

    factory { GetFavoritesUseCase(get()) }
    factory { AddToFavoriteUseCase(get()) }
    factory { RemoveFromFavoriteUseCase(get()) }

    factory { GetAllItemsInCartUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { RemoveFromCartUseCase(get()) }
    factory { UpdateCartQuantityUseCase(get()) }
    factory { ClearCartUseCase(get()) }

    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { DetailViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { FavoriteViewModel(get(), get()) }
    viewModel { CartViewModel(get(), get(), get(), get(), get()) }
}