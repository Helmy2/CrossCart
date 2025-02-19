package org.example.cross.card.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.features.details.data.repository.DetailsRepoImpl
import org.example.cross.card.features.details.domain.repository.DetailsRepo
import org.example.cross.card.features.details.domain.usecase.GetProductDetailsUseCase
import org.example.cross.card.features.details.presentation.DetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    single<DetailsRepo> {
        DetailsRepoImpl(
            supabase = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { GetProductDetailsUseCase(get()) }

    viewModel { DetailViewModel(get(), get(), get(), get(), get(), get()) }
}