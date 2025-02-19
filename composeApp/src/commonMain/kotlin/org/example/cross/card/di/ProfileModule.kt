package org.example.cross.card.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.features.auth.data.exception.AuthExceptionMapper
import org.example.cross.card.features.profile.data.repository.ProfileRepoImpl
import org.example.cross.card.features.profile.domain.repository.ProfileRepo
import org.example.cross.card.features.profile.domain.usecase.CurrentUserFlowUseCase
import org.example.cross.card.features.profile.domain.usecase.LogoutUseCase
import org.example.cross.card.features.profile.domain.usecase.UpdateNameUseCase
import org.example.cross.card.features.profile.domain.usecase.UpdateProfilePictureUseCase
import org.example.cross.card.features.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    single<ProfileRepo> {
        ProfileRepoImpl(
            auth = get(),
            storage = get(),
            exceptionMapper = AuthExceptionMapper(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { CurrentUserFlowUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { UpdateNameUseCase(get()) }
    factory { UpdateProfilePictureUseCase(get()) }

    viewModel { ProfileViewModel(get(), get(), get(), get(), get(), get()) }
}