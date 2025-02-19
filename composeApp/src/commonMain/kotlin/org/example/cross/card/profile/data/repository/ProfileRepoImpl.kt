package org.example.cross.card.profile.data.repository

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.UploadStatus
import io.github.jan.supabase.storage.updateAsFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.serialization.json.put
import org.example.cross.card.core.domain.entity.User
import org.example.cross.card.core.domain.entity.toDomainUser
import org.example.cross.card.core.domain.exceptions.ExceptionMapper
import org.example.cross.card.core.domain.util.DISPLAY_NAME_KEY
import org.example.cross.card.core.domain.util.PROFILE_PICTURE_KEY
import org.example.cross.card.profile.domain.repository.ProfileRepo

class ProfileRepoImpl(
    private val auth: Auth,
    private val storage: Storage,
    private val exceptionMapper: ExceptionMapper,
    private val dispatcher: CoroutineDispatcher,
) : ProfileRepo {

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


    override suspend fun signOut(): Result<Unit> = withContext(dispatcher) {
        try {
            auth.signOut()
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
}