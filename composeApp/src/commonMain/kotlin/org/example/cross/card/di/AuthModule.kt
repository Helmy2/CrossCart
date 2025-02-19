package org.example.cross.card.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.features.auth.data.exception.AuthExceptionMapper
import org.example.cross.card.features.auth.data.repository.AuthRepoImpl
import org.example.cross.card.features.auth.domain.repository.AuthRepo
import org.example.cross.card.features.auth.domain.usecase.IsUserLongedInFlowUseCase
import org.example.cross.card.features.auth.domain.usecase.LoginUseCase
import org.example.cross.card.features.auth.domain.usecase.RegisterUseCase
import org.example.cross.card.features.auth.domain.usecase.ResetPasswordUseCase
import org.example.cross.card.features.auth.domain.usecase.SignInAnonymouslyUseCase
import org.example.cross.card.features.auth.presentation.login.LoginViewModel
import org.example.cross.card.features.auth.presentation.register.RegisterViewModel
import org.example.cross.card.features.auth.presentation.resetPassword.ResetPasswordViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val authModule = module {

    single<AuthRepo> {
        AuthRepoImpl(
            auth = get(),
            exceptionMapper = AuthExceptionMapper(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { SignInAnonymouslyUseCase(get()) }
    factory { ResetPasswordUseCase(get()) }
    factory { IsUserLongedInFlowUseCase(get()) }

    viewModel { LoginViewModel(get(), get(), get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
    viewModel { ResetPasswordViewModel(get(), get(), get()) }
}



