package org.example.cross.card.di

import org.koin.dsl.module


val appModule = module {
    includes(coreModule)
    includes(supabaseModule)
    includes(productModule)
    includes(authModule)
}
