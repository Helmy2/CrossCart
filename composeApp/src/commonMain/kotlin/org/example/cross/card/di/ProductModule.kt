package org.example.cross.card.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.product.data.repository.ProductRepoImpl
import org.koin.dsl.module

val productModule = module {
    single {
        ProductRepoImpl(
            supabase = get(),
            dispatcher = Dispatchers.IO
        )
    }
}