package org.example.cross.card.auth.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.auth.domain.repository.AuthRepo

class IsUserLongedInFlowUseCase(private val repo: AuthRepo) {
    operator fun invoke(): Flow<Boolean> {
        return repo.isUserLongedIn()
    }
}