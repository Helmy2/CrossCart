package org.example.cross.card.favorite.data.repository


import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.cross.card.core.util.SupabaseConfig.FAVOURITE_TABLE
import org.example.cross.card.core.util.SupabaseConfig.PRODUCT_COLUMNS
import org.example.cross.card.core.util.SupabaseConfig.PRODUCT_TABLE
import org.example.cross.card.core.util.SupabaseConfig.THUMBNAIL_TABLE
import org.example.cross.card.favorite.data.model.FavoriteResponse
import org.example.cross.card.favorite.domain.repository.FavoriteRepo
import org.example.cross.card.product.data.model.ProductResponse
import org.example.cross.card.product.data.model.ThumbnailResponse
import org.example.cross.card.product.data.model.toDomain
import org.example.cross.card.product.domain.entity.Product


class FavoriteRepoImpl(
    private val supabase: SupabaseClient, private val dispatcher: CoroutineDispatcher
) : FavoriteRepo {

    private suspend fun getProductThumbnails(productId: String): Result<ThumbnailResponse?> =
        withContext(dispatcher) {
            runCatching {
                supabase.from(THUMBNAIL_TABLE).select {
                    filter {
                        ThumbnailResponse::productId eq productId
                    }
                }.decodeSingleOrNull<ThumbnailResponse>()
            }
        }

    @OptIn(SupabaseExperimental::class)
    override suspend fun getFavorites(): Flow<Result<List<Product>>> = withContext(dispatcher) {
        val userId = supabase.auth.currentUserOrNull()?.id
            ?: throw IllegalStateException("User is not logged in")

        supabase.from(FAVOURITE_TABLE).selectAsFlow(
            FavoriteResponse::productId, filter = FilterOperation(
                "user_id", FilterOperator.EQ, userId
            )
        ).map {
            runCatching {
                val columns = Columns.raw(PRODUCT_COLUMNS)
                supabase.from(PRODUCT_TABLE).select(
                    columns = columns,
                ) {
                    filter {
                        ProductResponse::id isIn it.map { it.productId }
                    }
                }.decodeList<ProductResponse>().map {
                    it.toDomain(getProductThumbnails(it.id).getOrNull())
                }
            }
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
                Unit
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
            Unit
        }
    }
}

