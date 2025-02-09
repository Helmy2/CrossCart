package org.example.cross.card.auth.domain.usecase

import org.example.cross.card.auth.domain.repository.AuthRepo

class UpdateNameUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(name: String): Result<Unit> {
        return repo.updateDisplayName(name)
    }
}