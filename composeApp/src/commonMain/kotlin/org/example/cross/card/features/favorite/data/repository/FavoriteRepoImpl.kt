package org.example.cross.card.features.favorite.data.repository


import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.cross.card.core.data.local.dao.ProductDao
import org.example.cross.card.core.data.local.entity.LocalFavorite
import org.example.cross.card.core.data.local.entity.toDomain
import org.example.cross.card.core.util.SupabaseConfig.FAVOURITE_TABLE
import org.example.cross.card.features.details.domain.entity.Product
import org.example.cross.card.features.favorite.data.model.FavoriteResponse
import org.example.cross.card.features.favorite.data.model.toLocalFavorite
import org.example.cross.card.features.favorite.domain.repository.FavoriteRepo


class FavoriteRepoImpl(
    private val supabase: SupabaseClient,
    private val productDao: ProductDao,
    private val dispatcher: CoroutineDispatcher
) : FavoriteRepo {

    @OptIn(SupabaseExperimental::class)
    override suspend fun getFavorites(): Flow<Result<List<Product>>> =
        productDao.getAllFavorites().map { list ->
            runCatching {
                list.mapNotNull {
                    productDao.getProduct(it.productId)?.toDomain()
                }
            }
        }.onStart {
            try {
                val userId = supabase.auth.currentUserOrNull()?.id
                    ?: throw IllegalStateException("User is not logged in")

                supabase.from(FAVOURITE_TABLE).selectAsFlow(
                    FavoriteResponse::productId, filter = FilterOperation(
                        "user_id", FilterOperator.EQ, userId
                    )
                ).map {
                    productDao.deleteFavorites()
                    productDao.insertFavorites(it.map { response -> response.toLocalFavorite() })
                }
            } catch (e: Exception) {
                println("Error fetching favorites: $e")
            }
        }

    override suspend fun removeFromFavorites(productId: String): Result<Unit> =
        withContext(dispatcher) {
            runCatching {
                supabase.auth.currentUserOrNull()?.id?.let { userId ->
                    supabase.from(FAVOURITE_TABLE).delete {
                        filter {
                            FavoriteResponse::userId eq userId
                            FavoriteResponse::productId eq productId
                        }
                    }
                }
                productDao.deleteFavorite(productId)
            }
        }

    override suspend fun addToFavorites(productId: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabase.auth.currentUserOrNull()?.id?.let { userId ->
                supabase.from(FAVOURITE_TABLE).insert(buildJsonObject {
                    put("user_id", userId)
                    put("product_id", productId)
                })
            }
            productDao.insertFavorite(LocalFavorite(productId))
        }
    }
}

