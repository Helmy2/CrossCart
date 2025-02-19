package org.example.cross.card.di

import org.koin.dsl.module


val appModule = module {
    includes(coreModule)
    includes(supabaseModule)
    includes(productModule)
    includes(authModule)
    includes(profileModule)
    includes(cartModule)
    includes(favoriteModule)
    includes(detailsModule)
}
