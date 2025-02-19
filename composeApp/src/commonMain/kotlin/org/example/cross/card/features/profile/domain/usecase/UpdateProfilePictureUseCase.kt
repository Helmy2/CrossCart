package org.example.cross.card.features.profile.domain.usecase

import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.flow.Flow
import org.example.cross.card.features.profile.domain.repository.ProfileRepo


class UpdateProfilePictureUseCase(private val repo: ProfileRepo) {
    suspend operator fun invoke(file: PlatformFile): Flow<Result<Float>> {
        return repo.updateProfilePicture(file.readBytes())
    }
}