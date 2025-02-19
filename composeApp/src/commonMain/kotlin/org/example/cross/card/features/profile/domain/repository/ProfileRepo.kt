package org.example.cross.card.features.profile.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.core.domain.entity.User

interface ProfileRepo {
    val currentUser: Flow<Result<User?>>

    suspend fun signOut(): Result<Unit>

    suspend fun updateDisplayName(name: String): Result<Unit>

    suspend fun updateProfilePicture(readBytes: ByteArray): Flow<Result<Float>>
}