@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.example.cross.card.core.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.example.cross.card.core.data.local.dao.ProductDao
import org.example.cross.card.core.data.local.entity.LocalFavorite
import org.example.cross.card.core.data.local.entity.LocalProduct


@Database(entities = [LocalProduct::class, LocalFavorite::class], version = 2)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<ProductDatabase> {
    override fun initialize(): ProductDatabase
}