package org.example.cross.card.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.features.cart.data.repository.CartRepoImpl
import org.example.cross.card.features.cart.domain.repository.CartRepo
import org.example.cross.card.features.cart.domain.usecase.AddToCartUseCase
import org.example.cross.card.features.cart.domain.usecase.ClearCartUseCase
import org.example.cross.card.features.cart.domain.usecase.GetAllItemsInCartUseCase
import org.example.cross.card.features.cart.domain.usecase.RemoveFromCartUseCase
import org.example.cross.card.features.cart.domain.usecase.UpdateCartQuantityUseCase
import org.example.cross.card.features.cart.presentation.CartViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val cartModule = module {
    single<CartRepo> {
        CartRepoImpl(
            supabase = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { GetAllItemsInCartUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { RemoveFromCartUseCase(get()) }
    factory { UpdateCartQuantityUseCase(get()) }
    factory { ClearCartUseCase(get()) }

    viewModel { CartViewModel(get(), get(), get(), get(), get()) }
}