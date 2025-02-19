package org.example.cross.card.profile.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.core.domain.entity.User
import org.example.cross.card.profile.domain.repository.ProfileRepo

class CurrentUserFlowUseCase(private val repo: ProfileRepo) {
    operator fun invoke(): Flow<Result<User?>> {
        return repo.currentUser
    }
}