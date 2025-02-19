package org.example.cross.card.features.auth.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun isUserLongedIn(): Flow<Boolean>

    suspend fun signInAnonymously(): Result<Unit>
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

    suspend fun resetPassword(email: String): Result<Unit>
}