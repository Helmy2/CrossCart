package org.example.cross.card.auth.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.cross.card.auth.domain.entity.User

interface AuthRepo {
    val currentUser: Flow<Result<User?>>

    fun isUserLongedIn(): Boolean

    suspend fun signInAnonymously(): Result<Unit>
    suspend fun signOut(): Result<Unit>
    suspend fun signInWithEmailAndPassword(
        email: String, password: String
    ): Result<Unit>

    suspend fun createUserWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ): Result<Unit>

    suspend fun convertToPermanentAccount(
        email: String, password: String
    ): Result<Unit>

    suspend fun updateDisplayName(name: String): Result<Unit>

    suspend fun resetPassword(email: String): Result<Unit>
}