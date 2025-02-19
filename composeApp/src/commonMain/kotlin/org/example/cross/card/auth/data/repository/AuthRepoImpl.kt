package org.example.cross.card.auth.data.repository

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.cross.card.auth.domain.repository.AuthRepo
import org.example.cross.card.core.domain.exceptions.ExceptionMapper
import org.example.cross.card.core.util.DISPLAY_NAME_KEY

class AuthRepoImpl(
    private val auth: Auth,
    private val exceptionMapper: ExceptionMapper,
    private val dispatcher: CoroutineDispatcher,
) : AuthRepo {

    override fun isUserLongedIn(): Flow<Boolean> {
        return channelFlow {
            auth.sessionStatus.collectLatest {
                when (it) {
                    is SessionStatus.Authenticated -> trySend(true)
                    is SessionStatus.NotAuthenticated -> trySend(false)
                    else -> {}
                }
            }
        }
    }


    override suspend fun signInAnonymously(): Result<Unit> = withContext(dispatcher) {
        try {
            auth.signInAnonymously()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String, password: String
    ): Result<Unit> = withContext(dispatcher) {
        try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        name: String, email: String, password: String
    ): Result<Unit> = withContext(dispatcher) {
        try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password

                data = buildJsonObject {
                    put(DISPLAY_NAME_KEY, name)
                }
                this.data
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override suspend fun convertToPermanentAccount(
        email: String, password: String
    ): Result<Unit> = withContext(dispatcher) {
        try {
            val currentUser =
                auth.currentUserOrNull() ?: throw IllegalStateException("No user logged in")

            if (currentUser.identities?.isNotEmpty() == true) {
                throw IllegalStateException("User already has a permanent account")
            }

            auth.updateUser {
                this.email = email
                this.password = password
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override suspend fun resetPassword(email: String): Result<Unit> = withContext(dispatcher) {
        try {
            auth.resetPasswordForEmail(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }
}
