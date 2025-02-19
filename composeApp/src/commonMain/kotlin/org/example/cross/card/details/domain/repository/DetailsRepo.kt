package org.example.cross.card.details.domain.repository

import org.example.cross.card.details.domain.entity.ProductDetails

interface DetailsRepo {
    suspend fun getProductById(productId: String): Result<ProductDetails?>
}