package org.example.cross.card.features.auth.domain.usecase

import org.example.cross.card.features.auth.domain.repository.AuthRepo

class SignInAnonymouslyUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(): Result<Unit> {
        return repo.signInAnonymously()
    }
}