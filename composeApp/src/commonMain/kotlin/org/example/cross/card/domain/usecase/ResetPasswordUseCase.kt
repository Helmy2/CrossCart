package org.example.cross.card.domain.usecase

import org.example.cross.card.domain.repository.AuthRepo

class ResetPasswordUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return repo.resetPassword(email)
    }
}