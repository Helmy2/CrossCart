package org.example.cross.card.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.cross.card.core.data.local.ProductDatabase
import org.example.cross.card.core.util.Connectivity
import org.example.cross.card.core.util.ConnectivityImp
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory


actual val platformModule: Module = module {
    single<Connectivity> {
        ConnectivityImp()
    }
    single<ProductDatabase> {
        val dbFile = NSHomeDirectory() + "/product.db"
        Room.databaseBuilder<ProductDatabase>(
            dbFile,
        ).fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO).build()
    }
}

