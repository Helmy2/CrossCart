package org.example.cross.card.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.example.cross.card.core.util.Connectivity
import org.example.cross.card.core.util.ConnectivityImp
import org.example.cross.card.features.home.data.local.ProductDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Connectivity> {
        ConnectivityImp(context = androidApplication())
    }
    single<ProductDatabase> {
        val dbFile = androidApplication().getDatabasePath("product.db")
        Room.databaseBuilder(
            androidApplication(),
            ProductDatabase::class.java,
            dbFile.absolutePath
        ).fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO).build()
    }
}
