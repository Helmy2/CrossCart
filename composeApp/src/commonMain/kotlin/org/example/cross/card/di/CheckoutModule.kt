package org.example.cross.card.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.features.checkout.data.repository.CheckoutRepoImpl
import org.example.cross.card.features.checkout.domain.repository.CheckoutRepo
import org.example.cross.card.features.checkout.domain.usecase.CreateOrderUseCase
import org.example.cross.card.features.checkout.presentation.CheckoutViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val checkoutModule = module {
    single<CheckoutRepo> {
        CheckoutRepoImpl(
            supabase = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { CreateOrderUseCase(get()) }

    viewModel { CheckoutViewModel(get(), get(), get(), get()) }
}