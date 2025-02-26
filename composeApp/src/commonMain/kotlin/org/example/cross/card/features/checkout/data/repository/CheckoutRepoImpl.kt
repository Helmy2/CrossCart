package org.example.cross.card.features.checkout.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.example.cross.card.core.util.SupabaseConfig
import org.example.cross.card.features.checkout.data.models.OrderItem
import org.example.cross.card.features.checkout.data.models.OrderResponse
import org.example.cross.card.features.checkout.domain.models.Address
import org.example.cross.card.features.checkout.domain.repository.CheckoutRepo

class CheckoutRepoImpl(
    private val supabase: SupabaseClient, private val dispatcher: CoroutineDispatcher
) : CheckoutRepo {
    override suspend fun createOrder(
        orderItems: List<OrderItem>,
        shippingAddress: Address
    ): Result<Unit> =
        withContext(dispatcher) {
            supabase.auth.currentUserOrNull()?.id?.let {
                runCatching {
                    supabase.from(SupabaseConfig.ORDER_TABLE).insert(
                        OrderResponse(
                            userId = it,
                            orderItems = orderItems,
                            shippingAddress = shippingAddress,
                        )
                    )
                    Unit
                }
            } ?: throw Exception("User not logged in")
        }
}