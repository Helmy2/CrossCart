package org.example.cross.card.details.data.repository


import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.example.cross.card.cart.data.model.CartResponse
import org.example.cross.card.core.util.SupabaseConfig.CART_TABLE
import org.example.cross.card.core.util.SupabaseConfig.FAVOURITE_TABLE
import org.example.cross.card.core.util.SupabaseConfig.IMAGE_TABLE
import org.example.cross.card.core.util.SupabaseConfig.PRODUCT_DETAILS_COLUMNS
import org.example.cross.card.core.util.SupabaseConfig.PRODUCT_TABLE
import org.example.cross.card.details.data.model.ImageResponse
import org.example.cross.card.details.data.model.ProductDetailsResponse
import org.example.cross.card.details.data.model.toDomain
import org.example.cross.card.details.domain.entity.ProductDetails
import org.example.cross.card.details.domain.repository.DetailsRepo
import org.example.cross.card.favorite.data.model.FavoriteResponse


class DetailsRepoImpl(
    private val supabase: SupabaseClient, private val dispatcher: CoroutineDispatcher
) : DetailsRepo {

    override suspend fun getProductById(productId: String): Result<ProductDetails?> =
        withContext(dispatcher) {
            runCatching {
                val isFavorite = isProductFavorite(productId).getOrThrow()
                val inCart = isProductInCart(productId).getOrThrow()
                val images = getProductImages(productId).getOrNull().orEmpty()

                val columns = Columns.raw(PRODUCT_DETAILS_COLUMNS)
                supabase.from(PRODUCT_TABLE).select(columns = columns) {
                    filter { ProductDetailsResponse::id eq productId }
                }.decodeSingleOrNull<ProductDetailsResponse>()?.toDomain(
                    imageUrls = images, isFavorite = isFavorite, inCart = inCart
                )
            }
        }


    private suspend fun getProductImages(productId: String): Result<List<ImageResponse>> =
        withContext(dispatcher) {
            runCatching {
                supabase.from(IMAGE_TABLE).select {
                    filter {
                        ImageResponse::productId eq productId
                    }
                }.decodeList<ImageResponse>()
            }
        }

    private suspend fun isProductFavorite(productId: String): Result<Boolean> =
        withContext(dispatcher) {
            runCatching {
                supabase.from(FAVOURITE_TABLE).select() {
                    filter {
                        FavoriteResponse::userId eq supabase.auth.currentUserOrNull()?.id
                        FavoriteResponse::productId eq productId
                    }
                }.decodeList<FavoriteResponse>().isNotEmpty()
            }
        }


    private suspend fun isProductInCart(productId: String): Result<Boolean> =
        withContext(dispatcher) {
            runCatching {
                supabase.from(CART_TABLE).select {
                    filter {
                        CartResponse::userId eq supabase.auth.currentUserOrNull()?.id
                        CartResponse::productId eq productId
                    }
                }.decodeList<CartResponse>().isNotEmpty()
            }
        }
}

