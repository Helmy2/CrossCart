package org.example.cross.card.di

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.createSupabaseClient
import org.example.cross.card.BuildKonfig
import org.example.cross.card.data.exception.AuthExceptionMapper
import org.example.cross.card.data.repository.AuthRepoImpl
import org.example.cross.card.domain.repository.AuthRepo
import org.example.cross.card.domain.usecase.CurrentUserFlowUseCase
import org.example.cross.card.domain.usecase.LoginUseCase
import org.example.cross.card.domain.usecase.LogoutUseCase
import org.example.cross.card.domain.usecase.RegisterUseCase
import org.example.cross.card.domain.usecase.ResetPasswordUseCase
import org.example.cross.card.domain.usecase.SignInAnonymouslyUseCase
import org.example.cross.card.domain.util.PlatformDispatchers
import org.koin.dsl.module

val authModule = module {
    factory { CurrentUserFlowUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { SignInAnonymouslyUseCase(get()) }
    factory { ResetPasswordUseCase(get()) }

    single<AuthRepo> {
        AuthRepoImpl(
            auth = get(),
            exceptionMapper = AuthExceptionMapper(),
            dispatcher = PlatformDispatchers.Io
        )
    }
}

val supabaseModule = module {
    single<SupabaseClient> {
        val supabase = createSupabaseClient(
            supabaseKey = BuildKonfig.supabaseKey,
            supabaseUrl = BuildKonfig.supabaseUrl
        ) {
            install(Auth)
            install(ComposeAuth)
        }
        supabase
    }

    single { get<SupabaseClient>().auth }
    single { get<SupabaseClient>().composeAuth }

    single<String> {
        BuildKonfig.supabaseKey
    }
}

val appModule = module {
    includes(authModule)
    includes(supabaseModule)
}
