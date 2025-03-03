package org.example.cross.card.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    includes(coreModule)
    includes(coreModule)
    includes(supabaseModule)
    includes(homeModule)
    includes(authModule)
    includes(profileModule)
    includes(cartModule)
    includes(favoriteModule)
    includes(detailsModule)
    includes(checkoutModule)
}
