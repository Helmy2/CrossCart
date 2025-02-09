package org.example.cross.card.product.data.util

object SupabaseConfig {
    const val PRODUCT_TABLE = "products"
    const val CATEGORY_TABLE = "categories"
    const val IMAGE_TABLE = "product_images"
    const val THUMBNAIL_TABLE = "product_thumbnails"

    const val PRODUCT_COLUMNS = "id, title, price,rating, discount_percentage, category_id"
    const val PRODUCT_DETAILS_COLUMNS =
        "id, title, price, discount_percentage, description,rating, brand, stock, warranty_information," +
                "shipping_information, availability_status, return_policy,minimum_order_quantity, categories(id, name)"
}