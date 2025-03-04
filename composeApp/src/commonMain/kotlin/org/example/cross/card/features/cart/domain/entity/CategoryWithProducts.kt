package org.example.cross.card.features.cart.domain.entity

import org.example.cross.card.features.details.domain.entity.Product

data class CategoryWithProducts(val categoryName: String, val products: List<Product>)