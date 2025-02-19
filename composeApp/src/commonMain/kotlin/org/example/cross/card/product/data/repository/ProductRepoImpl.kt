package org.example.cross.card.product.data.repository


import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.example.cross.card.cart.data.model.CartResponse
import org.example.cross.card.core.util.SupabaseConfig.CART_TABLE
import org.example.cross.card.core.util.SupabaseConfig.CATEGORY_TABLE
import org.example.cross.card.core.util.SupabaseConfig.FAVOURITE_TABLE
import org.example.cross.card.core.util.SupabaseConfig.IMAGE_TABLE
import org.example.cross.card.core.util.SupabaseConfig.PRODUCT_COLUMNS
import org.example.cross.card.core.util.SupabaseConfig.PRODUCT_DETAILS_COLUMNS
import org.example.cross.card.core.util.SupabaseConfig.PRODUCT_TABLE
import org.example.cross.card.core.util.SupabaseConfig.THUMBNAIL_TABLE
import org.example.cross.card.favorite.data.model.FavoriteResponse
import org.example.cross.card.product.data.model.CategoryResponse
import org.example.cross.card.product.data.model.ImageResponse
import org.example.cross.card.product.data.model.ProductDetailsResponse
import org.example.cross.card.product.data.model.ProductResponse
import org.example.cross.card.product.data.model.ThumbnailResponse
import org.example.cross.card.product.data.model.toDomain
import org.example.cross.card.product.domain.entity.Category
import org.example.cross.card.product.domain.entity.OrderBy
import org.example.cross.card.product.domain.entity.Product
import org.example.cross.card.product.domain.entity.ProductDetails
import org.example.cross.card.product.domain.entity.toSupabaseOrder
import org.example.cross.card.product.domain.repository.ProductRepo


class ProductRepoImpl(
    private val supabase: SupabaseClient, private val dispatcher: CoroutineDispatcher
) : ProductRepo {

    override suspend fun getAllProducts(): Result<List<Product>> = withContext(dispatcher) {
        runCatching {
            val columns = Columns.raw(PRODUCT_COLUMNS)
            supabase.from(PRODUCT_TABLE).select(
                columns = columns,
            ).decodeList<ProductResponse>().map {
                it.toDomain(getProductThumbnails(it.id).getOrNull())
            }
        }
    }

    override suspend fun getProductsByName(
        query: String, rating: Float?, fromPrice: Float?, toPrice: Float?, orderBy: OrderBy
    ): Result<List<Product>> = withContext(dispatcher) {
        runCatching {
            val columns = Columns.raw(PRODUCT_COLUMNS)
            supabase.from(PRODUCT_TABLE).select(
                columns = columns,
            ) {
                filter {
                    and {
                        ProductResponse::title ilike "%$query%"
                        rating?.let { ProductResponse::rating gte it }
                        fromPrice?.let { ProductResponse::price gte it }
                        toPrice?.let { ProductResponse::price lte it }
                    }
                }
                when (orderBy) {
                    is OrderBy.Name -> order(
                        ProductResponse::rating.name, order = orderBy.order.toSupabaseOrder()
                    )

                    is OrderBy.Price -> order(
                        ProductResponse::price.name, order = orderBy.order.toSupabaseOrder()
                    )

                    is OrderBy.Rating -> order(
                        ProductResponse::rating.name, order = orderBy.order.toSupabaseOrder()
                    )
                }
            }.decodeList<ProductResponse>().map {
                it.toDomain(getProductThumbnails(it.id).getOrNull())
            }
        }
    }


    override suspend fun filterProductsByCategory(categoryId: String): Result<List<Product>> =
        withContext(dispatcher) {
            runCatching {
                val columns = Columns.raw(PRODUCT_COLUMNS)

                supabase.from(PRODUCT_TABLE).select(
                    columns = columns,
                ) {
                    filter { ProductResponse::categoryId eq categoryId }
                }.decodeList<ProductResponse>().map {
                    it.toDomain(getProductThumbnails(it.id).getOrNull())
                }
            }
        }


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

    override suspend fun getAllCategories(): Result<List<Category>> = withContext(dispatcher) {
        runCatching {
            supabase.from(CATEGORY_TABLE).select().decodeList<CategoryResponse>().map {
                it.toDomain()
            }
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

