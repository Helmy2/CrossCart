package org.example.cross.card.features.home.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.example.cross.card.features.home.data.local.entity.LocalProduct

@Dao
interface ProductDao {

    @Upsert
    suspend fun insert(list: List<LocalProduct>)

    @Query("SELECT * FROM localproduct")
    fun getAllProducts(): Flow<List<LocalProduct>>

    @Query("Delete FROM localproduct")
    suspend fun delete()
}