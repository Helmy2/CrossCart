package org.example.cross.card.features.auth.domain.usecase

import org.example.cross.card.features.auth.domain.repository.AuthRepo


class RegisterUseCase(
    private val repo: AuthRepo,
) {
    suspend operator fun invoke(
        email: String, password: String, name: String
    ): Result<Unit> {
        return repo.createUserWithEmailAndPassword(name, email, password)
    }
}