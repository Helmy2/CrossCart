package org.example.cross.card.auth.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.auth.domain.entity.User
import org.example.cross.card.auth.domain.repository.AuthRepo

class CurrentUserFlowUseCase(private val repo: AuthRepo) {
    operator fun invoke(): Flow<Result<User?>> {
        return repo.currentUser
    }
}