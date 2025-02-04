package org.example.cross.card.core.domain.usecase

import org.example.cross.card.auth.domain.repository.AuthRepo

class IsUserLongedInUseCase(private val repo: AuthRepo) {
    operator fun invoke(): Boolean {
        return repo.isUserLongedIn()
    }
}