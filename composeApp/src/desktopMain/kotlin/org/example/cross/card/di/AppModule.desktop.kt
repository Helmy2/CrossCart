package org.example.cross.card.di

import org.example.cross.card.core.util.Connectivity
import org.example.cross.card.core.util.ConnectivityImp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Connectivity> {
        ConnectivityImp()
    }
}
