package org.example.cross.card.auth.domain.usecase

import io.github.vinceglb.filekit.core.PlatformFile
import io.github.vinceglb.filekit.core.extension
import kotlinx.coroutines.flow.Flow
import org.example.cross.card.auth.domain.repository.AuthRepo


class UpdateProfilePictureUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(file: PlatformFile): Flow<Result<Float>> {
        return repo.updateProfilePicture(file.readBytes(), file.extension)
    }
}