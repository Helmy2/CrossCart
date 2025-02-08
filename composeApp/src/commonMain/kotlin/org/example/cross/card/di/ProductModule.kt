package org.example.cross.card.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.product.data.repository.ProductRepoImpl
import org.example.cross.card.product.domain.repository.ProductRepo
import org.example.cross.card.product.domain.usecase.GetCategoriesWithProductsUseCase
import org.example.cross.card.product.domain.usecase.GetProductByIdUseCase
import org.example.cross.card.product.domain.usecase.GetProductsByNameUseCase
import org.example.cross.card.product.presentation.details.DetailViewModel
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

    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
}