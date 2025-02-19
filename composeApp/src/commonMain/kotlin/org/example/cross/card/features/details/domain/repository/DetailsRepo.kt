package org.example.cross.card.features.details.domain.repository

import org.example.cross.card.features.details.domain.entity.ProductDetails

interface DetailsRepo {
    suspend fun getProductById(productId: String): Result<ProductDetails?>
}