package org.example.cross.card.di

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.example.cross.card.BuildKonfig
import org.koin.dsl.module

val supabaseModule = module {
    single<SupabaseClient> {
        val supabase = createSupabaseClient(
            supabaseKey = BuildKonfig.supabaseKey,
            supabaseUrl = BuildKonfig.supabaseUrl
        ) {
            install(Auth)
            install(ComposeAuth)
            install(Postgrest)
        }
        supabase
    }
}