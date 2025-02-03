package org.example.cross.card.product.data.repository


import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.example.cross.card.product.data.model.CategoryResponse
import org.example.cross.card.product.data.model.ImageResponse
import org.example.cross.card.product.data.model.ProductDetailsResponse
import org.example.cross.card.product.data.model.ProductResponse
import org.example.cross.card.product.data.model.toDomain
import org.example.cross.card.product.data.util.SupabaseConfig.CATEGORY_TABLE
import org.example.cross.card.product.data.util.SupabaseConfig.IMAGE_TABLE
import org.example.cross.card.product.data.util.SupabaseConfig.PRODUCT_COLUMNS
import org.example.cross.card.product.data.util.SupabaseConfig.PRODUCT_DETAILS_COLUMNS
import org.example.cross.card.product.data.util.SupabaseConfig.PRODUCT_TABLE
import org.example.cross.card.product.domain.entity.Category
import org.example.cross.card.product.domain.entity.Product
import org.example.cross.card.product.domain.entity.ProductDetails
import org.example.cross.card.product.domain.repository.ProductRepo


class ProductRepoImpl(
    private val supabase: SupabaseClient, private val dispatcher: CoroutineDispatcher
) : ProductRepo {

    override suspend fun getAllProducts(): Result<List<Product>> = withContext(dispatcher) {
        runCatching {
            val columns = Columns.raw(PRODUCT_COLUMNS)
            supabase.from(PRODUCT_TABLE).select(
                columns = columns,
            ).decodeList<ProductResponse>()
                .map {
                    it.toDomain(getProductImages(it.id).getOrNull()?.firstOrNull())
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
                    it.toDomain(getProductImages(it.id).getOrNull()?.firstOrNull())
                }
            }
        }


    override suspend fun getProductById(productId: String): Result<ProductDetails?> =
        withContext(dispatcher) {
            runCatching {
                val columns = Columns.raw(PRODUCT_DETAILS_COLUMNS)

                supabase.from(PRODUCT_TABLE).select(
                    columns = columns
                ) {
                    filter {
                        ProductDetailsResponse::id eq productId
                    }
                }.decodeSingleOrNull<ProductDetailsResponse>()?.let {
                    it.toDomain(getProductImages(it.id).getOrNull().orEmpty())
                }
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

    override suspend fun getAllCategories(): Result<List<Category>> = withContext(dispatcher) {
        runCatching {
            supabase.from(CATEGORY_TABLE).select().decodeList<CategoryResponse>().map {
                it.toDomain()
            }
        }
    }
}

