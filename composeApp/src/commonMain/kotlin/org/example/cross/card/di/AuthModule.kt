package org.example.cross.card.di

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.compose.auth.composeAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.auth.data.exception.AuthExceptionMapper
import org.example.cross.card.auth.data.repository.AuthRepoImpl
import org.example.cross.card.auth.domain.repository.AuthRepo
import org.example.cross.card.auth.domain.usecase.CurrentUserFlowUseCase
import org.example.cross.card.auth.domain.usecase.LoginUseCase
import org.example.cross.card.auth.domain.usecase.LogoutUseCase
import org.example.cross.card.auth.domain.usecase.RegisterUseCase
import org.example.cross.card.auth.domain.usecase.ResetPasswordUseCase
import org.example.cross.card.auth.domain.usecase.SignInAnonymouslyUseCase
import org.example.cross.card.auth.domain.usecase.UpdateNameUseCase
import org.example.cross.card.auth.presentation.login.LoginViewModel
import org.example.cross.card.auth.presentation.profile.ProfileViewModel
import org.example.cross.card.auth.presentation.register.RegisterViewModel
import org.example.cross.card.auth.presentation.resetPassword.ResetPasswordViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val authModule = module {
    single { get<SupabaseClient>().auth }
    single { get<SupabaseClient>().composeAuth }

    single<AuthRepo> {
        AuthRepoImpl(
            auth = get(),
            exceptionMapper = AuthExceptionMapper(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { CurrentUserFlowUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { SignInAnonymouslyUseCase(get()) }
    factory { ResetPasswordUseCase(get()) }
    factory { UpdateNameUseCase(get()) }

    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
    viewModel { ResetPasswordViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get(), get(), get()) }
}



