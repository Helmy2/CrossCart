package org.example.cross.card.di

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import org.example.cross.card.BuildKonfig
import org.koin.dsl.module

val supabaseModule = module {
    single<SupabaseClient> {
        createSupabaseClient(
            supabaseKey = BuildKonfig.supabaseKey,
            supabaseUrl = BuildKonfig.supabaseUrl
        ) {
            install(Auth)
            install(ComposeAuth)
            install(Postgrest)
            install(Realtime)
            install(Storage)
            install(ComposeAuth) {
                googleNativeLogin(serverClientId = BuildKonfig.serverClientId)
            }
        }
    }

    single { get<SupabaseClient>().auth }
    single { get<SupabaseClient>().composeAuth }
    single { get<SupabaseClient>().storage }
}