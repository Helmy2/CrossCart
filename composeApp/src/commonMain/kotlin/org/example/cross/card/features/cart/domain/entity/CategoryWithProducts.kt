package org.example.cross.card.features.cart.domain.entity

import org.example.cross.card.features.details.domain.entity.Category
import org.example.cross.card.features.details.domain.entity.Product

data class CategoryWithProducts(val category: Category, val products: List<Product>)