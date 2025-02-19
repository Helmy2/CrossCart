package org.example.cross.card.cart.data.repository


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
import org.example.cross.card.cart.data.model.CartResponse
import org.example.cross.card.cart.domain.entity.CartItem
import org.example.cross.card.cart.domain.repository.CartRepo
import org.example.cross.card.core.util.SupabaseConfig.CART_TABLE
import org.example.cross.card.core.util.SupabaseConfig.PRODUCT_COLUMNS
import org.example.cross.card.core.util.SupabaseConfig.PRODUCT_TABLE
import org.example.cross.card.core.util.SupabaseConfig.THUMBNAIL_TABLE
import org.example.cross.card.favorite.data.model.FavoriteResponse
import org.example.cross.card.product.data.model.ProductResponse
import org.example.cross.card.product.data.model.ThumbnailResponse
import org.example.cross.card.product.data.model.toDomain


class CartRepoImpl(
    private val supabase: SupabaseClient, private val dispatcher: CoroutineDispatcher
) : CartRepo {

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


    override suspend fun updateCartQuantity(productId: String, quantity: Int): Result<Unit> =
        withContext(dispatcher) {
            runCatching {
                supabase.auth.currentUserOrNull()?.id?.let { userId ->
                    supabase.from(CART_TABLE).update(buildJsonObject {
                        put("amount", quantity)
                    }) {
                        filter {
                            CartResponse::userId eq userId
                            CartResponse::productId eq productId
                        }
                    }
                }
                Unit
            }
        }

    @OptIn(SupabaseExperimental::class)
    override suspend fun getAllItemsInCart(): Flow<Result<List<CartItem>>> =
        withContext(dispatcher) {
            val userId = supabase.auth.currentUserOrNull()?.id
                ?: throw IllegalStateException("User is not logged in")

            supabase.from(CART_TABLE).selectAsFlow(
                CartResponse::productId, filter = FilterOperation(
                    "user_id", FilterOperator.EQ, userId
                )
            ).map { list ->
                runCatching {
                    val columns = Columns.raw(PRODUCT_COLUMNS)
                    supabase.from(PRODUCT_TABLE).select(
                        columns = columns,
                    ) {
                        filter {
                            ProductResponse::id isIn list.map { it.productId }
                        }
                    }.decodeList<ProductResponse>().map { productResponse ->
                        CartItem(
                            product = productResponse.toDomain(
                                getProductThumbnails(
                                    productResponse.id
                                ).getOrNull()
                            ),
                            quantity = list.first { it.productId == productResponse.id }.amount,
                        )
                    }
                }
            }
        }

    override suspend fun clearCart(): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabase.auth.currentUserOrNull()?.id?.let { userId ->
                supabase.from(CART_TABLE).delete {
                    filter {
                        FavoriteResponse::userId eq userId
                    }
                }
            }
            Unit
        }
    }

    override suspend fun addToCart(productId: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabase.auth.currentUserOrNull()?.id?.let { userId ->
                supabase.from(CART_TABLE).insert(buildJsonObject {
                    put("user_id", userId)
                    put("product_id", productId)
                })
            }
            Unit
        }
    }

    override suspend fun removeFromCart(productId: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabase.auth.currentUserOrNull()?.id?.let { userId ->
                supabase.from(CART_TABLE).delete {
                    filter {
                        FavoriteResponse::userId eq userId
                        FavoriteResponse::productId eq productId
                    }
                }
            }
            Unit
        }
    }
}

