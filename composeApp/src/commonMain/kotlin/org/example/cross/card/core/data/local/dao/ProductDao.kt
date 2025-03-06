package org.example.cross.card.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.example.cross.card.core.data.local.entity.LocalFavorite
import org.example.cross.card.core.data.local.entity.LocalProduct

@Dao
interface ProductDao {

    @Upsert
    suspend fun insert(list: List<LocalProduct>)

    @Query("SELECT * FROM localproduct")
    fun getAllProducts(): Flow<List<LocalProduct>>

    @Query("Delete FROM localproduct")
    suspend fun delete()

    @Query("SELECT * FROM localproduct WHERE id = :id")
    suspend fun getProduct(id: String): LocalProduct?

    @Upsert
    suspend fun insertFavorites(favorites: List<LocalFavorite>)

    @Upsert
    suspend fun insertFavorite(favorites: LocalFavorite)

    @Query("Delete FROM localfavorite WHERE productId = :productId")
    suspend fun deleteFavorite(productId: String)

    @Query("SELECT * FROM localfavorite")
    fun getAllFavorites(): Flow<List<LocalFavorite>>

    @Query("Delete FROM localfavorite")
    suspend fun deleteFavorites()
}