package org.example.cross.card.features.checkout.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.example.cross.card.core.util.SupabaseConfig
import org.example.cross.card.features.checkout.data.models.OrderResponse
import org.example.cross.card.features.checkout.domain.repository.CheckoutRepo

class CheckoutRepoImpl(
    private val supabase: SupabaseClient, private val dispatcher: CoroutineDispatcher
) : CheckoutRepo {
    override suspend fun createOrder(orderResponse: OrderResponse): Result<Unit> =
        withContext(dispatcher) {
            runCatching {
                supabase.from(SupabaseConfig.ORDER_TABLE).insert(orderResponse)
                Unit
            }
        }
}