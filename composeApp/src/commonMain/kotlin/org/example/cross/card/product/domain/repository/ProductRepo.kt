package org.example.cross.card.product.domain.repository

import org.example.cross.card.product.domain.entity.Category
import org.example.cross.card.product.domain.entity.Product
import org.example.cross.card.product.domain.entity.ProductDetails

interface ProductRepo {
    suspend fun getAllProducts(): Result<List<Product>>
    suspend fun filterProductsByCategory(categoryId: String): Result<List<Product>>
    suspend fun getProductById(productId: String): Result<ProductDetails?>
    suspend fun getAllCategories(): Result<List<Category>>
}