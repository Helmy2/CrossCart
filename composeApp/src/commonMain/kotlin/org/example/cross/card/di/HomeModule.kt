package org.example.cross.card.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.features.home.data.local.ProductDatabase
import org.example.cross.card.features.home.data.local.dao.ProductDao
import org.example.cross.card.features.home.data.repository.HomeRepoImpl
import org.example.cross.card.features.home.domain.repository.HomeRepo
import org.example.cross.card.features.home.domain.usecase.GetCategoriesWithProductsUseCase
import org.example.cross.card.features.home.domain.usecase.GetProductsByNameUseCase
import org.example.cross.card.features.home.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single<HomeRepo> {
        HomeRepoImpl(
            supabase = get(),
            productDao = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory<ProductDao> {
        get<ProductDatabase>().productDao()
    }

    factory { GetCategoriesWithProductsUseCase(get()) }
    factory { GetProductsByNameUseCase(get()) }

    viewModel { HomeViewModel(get(), get(), get(), get()) }
}