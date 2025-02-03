package org.example.cross.card.product.data.util

object SupabaseConfig {
    const val PRODUCT_TABLE = "products"
    const val CATEGORY_TABLE = "categories"
    const val IMAGE_TABLE = "product_images"

    const val PRODUCT_COLUMNS = "id, title,price, discount, popular, category_id"
    const val PRODUCT_DETAILS_COLUMNS =
        "id, title, price, discount, popular, description, brand, model, categories(id, name, image)"
}