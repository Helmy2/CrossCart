package org.example.cross.card.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.features.favorite.data.repository.FavoriteRepoImpl
import org.example.cross.card.features.favorite.domain.repository.FavoriteRepo
import org.example.cross.card.features.favorite.domain.usecase.AddToFavoriteUseCase
import org.example.cross.card.features.favorite.domain.usecase.GetFavoritesUseCase
import org.example.cross.card.features.favorite.domain.usecase.RemoveFromFavoriteUseCase
import org.example.cross.card.features.favorite.presentation.FavoriteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    single<FavoriteRepo> {
        FavoriteRepoImpl(
            supabase = get(),
            productDao = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { GetFavoritesUseCase(get()) }
    factory { AddToFavoriteUseCase(get()) }
    factory { RemoveFromFavoriteUseCase(get()) }

    viewModel { FavoriteViewModel(get(), get()) }
}