package org.example.cross.card.auth.data.repository

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.cross.card.auth.domain.entity.User
import org.example.cross.card.auth.domain.repository.AuthRepo
import org.example.cross.card.auth.domain.util.DISPLAY_NAME_KEY
import org.example.cross.card.auth.domain.util.toDomainUser
import org.example.cross.card.core.domain.exceptions.ExceptionMapper

class AuthRepoImpl(
    private val auth: Auth,
    private val exceptionMapper: ExceptionMapper,
    private val dispatcher: CoroutineDispatcher,
) : AuthRepo {

    override val currentUser: Flow<Result<User?>> = auth.sessionStatus.map {
        try {
            val user = auth.currentUserOrNull()?.toDomainUser()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }.catch { e ->
        emit(Result.failure(exceptionMapper.map(e)))
    }

    override fun isUserLongedIn(): Boolean {
        return auth.currentUserOrNull() != null
    }


    override suspend fun signInAnonymously(): Result<Unit> = withContext(dispatcher) {
        try {
            auth.signInAnonymously()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override suspend fun signOut(): Result<Unit> = withContext(dispatcher) {
        try {
            auth.signOut()
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

    override suspend fun updateDisplayName(name: String): Result<Unit> = withContext(dispatcher) {
        try {
            auth.updateUser {
                data {
                    put(DISPLAY_NAME_KEY, name)
                }
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
