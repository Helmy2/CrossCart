package org.example.cross.card.cart.domain.entity

import org.example.cross.card.product.domain.entity.Category
import org.example.cross.card.product.domain.entity.Product

data class CategoryWithProducts(val category: Category, val products: List<Product>)