package org.example.cross.card.profile.domain.usecase

import org.example.cross.card.profile.domain.repository.ProfileRepo

class LogoutUseCase(private val repo: ProfileRepo) {
    suspend operator fun invoke(): Result<Unit> {
        return repo.signOut()
    }
}