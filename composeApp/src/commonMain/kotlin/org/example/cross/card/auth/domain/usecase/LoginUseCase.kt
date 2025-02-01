package org.example.cross.card.auth.domain.usecase

import org.example.cross.card.auth.domain.repository.AuthRepo

class LoginUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repo.signInWithEmailAndPassword(email, password)
    }
}