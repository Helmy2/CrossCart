package org.example.cross.card.profile.domain.usecase

import org.example.cross.card.profile.domain.repository.ProfileRepo

class UpdateNameUseCase(private val repo: ProfileRepo) {
    suspend operator fun invoke(name: String): Result<Unit> {
        return repo.updateDisplayName(name)
    }
}