package org.example.cross.card.core.domain.usecase

import kotlinx.coroutines.flow.first
import org.example.cross.card.features.auth.domain.repository.AuthRepo

class IsUserLongedInUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(): Boolean {
        return repo.isUserLongedIn().first()
    }
}