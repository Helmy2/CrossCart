package org.example.cross.card.auth.data.repository

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.UploadStatus
import io.github.jan.supabase.storage.updateAsFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.cross.card.auth.domain.entity.User
import org.example.cross.card.auth.domain.repository.AuthRepo
import org.example.cross.card.auth.domain.util.DISPLAY_NAME_KEY
import org.example.cross.card.auth.domain.util.PROFILE_PICTURE_KEY
import org.example.cross.card.auth.domain.util.toDomainUser
import org.example.cross.card.core.domain.exceptions.ExceptionMapper

class AuthRepoImpl(
    private val auth: Auth,
    private val storage: Storage,
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

    override suspend fun updateProfilePicture(
        readBytes: ByteArray,
    ): Flow<Result<Float>> {
        val bucket = storage.from("user_images")
        val currentUser = auth.currentUserOrNull()?.toDomainUser()
        val imageUri = currentUser?.profilePicture

        if (imageUri != null) {
            val url = bucket.publicUrl("")
            bucket.delete(imageUri.replace(url, ""))
        }
        val randomFileName = "${Clock.System.now().toEpochMilliseconds()}.jpg"

        return bucket.updateAsFlow(randomFileName, readBytes).map {
            when (it) {
                is UploadStatus.Progress -> {
                    Result.success(it.totalBytesSend.toFloat() / it.contentLength * 100)
                }

                is UploadStatus.Success -> {
                    val url = bucket.publicUrl(randomFileName)
                    updateProfilePicture(url)
                    Result.success(100f)
                }
            }
        }.catch {
            emit(Result.failure(exceptionMapper.map(it)))
        }
    }

    private suspend fun updateProfilePicture(url: String): Result<Unit> = withContext(dispatcher) {
        try {
            auth.updateUser {
                data {
                    put(PROFILE_PICTURE_KEY, url)
                }
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
