package org.example.cross.card.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.example.cross.card.core.util.Connectivity
import org.example.cross.card.core.util.ConnectivityImp
import org.example.cross.card.features.home.data.local.ProductDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val platformModule: Module = module {
    single<Connectivity> {
        ConnectivityImp()
    }
    single<ProductDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "product.db")
        Room.databaseBuilder<ProductDatabase>(
            dbFile.absolutePath,
        ).fallbackToDestructiveMigrationOnDowngrade(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO).build()
    }
}
