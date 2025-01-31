package org.example.cross.card.domain.usecase

import org.example.cross.card.domain.repository.AuthRepo

class LogoutUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(): Result<Unit> {
        return repo.signOut()
    }
}