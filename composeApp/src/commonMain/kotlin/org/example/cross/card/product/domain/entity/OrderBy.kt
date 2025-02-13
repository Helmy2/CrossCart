package org.example.cross.card.product.domain.entity

import io.github.jan.supabase.postgrest.query.Order.ASCENDING
import io.github.jan.supabase.postgrest.query.Order.DESCENDING

sealed class OrderBy(val name: String, val order: Order) {
    companion object {
        val default = Name(Order.Ascending)
        val all = listOf(Name(Order.Ascending), Price(Order.Ascending), Rating(Order.Ascending))


    }

    fun copy(order: Order) = when (this) {
        is Name -> Name(order)
        is Price -> Price(order)
        is Rating -> Rating(order)
    }

    class Name(order: Order) : OrderBy(name = "Name", order)
    class Price(order: Order) : OrderBy(name = "Price", order)
    class Rating(order: Order) : OrderBy(name = "Rating", order)


}

sealed class Order {
    data object Ascending : Order()
    data object Descending : Order()
}

fun Order.toSupabaseOrder() = when (this) {
    Order.Ascending -> ASCENDING
    Order.Descending -> DESCENDING
}