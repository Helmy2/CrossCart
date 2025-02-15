package org.example.cross.card.auth.domain.util

import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.json.jsonPrimitive
import org.example.cross.card.auth.domain.entity.User

fun UserInfo.toDomainUser(): User = User(
    id = this.id,
    name = userMetadata?.get(DISPLAY_NAME_KEY)?.jsonPrimitive?.content ?: ANONYMOUS_USER_NAME,
    profilePicture = userMetadata?.get(PROFILE_PICTURE_KEY)?.jsonPrimitive?.content,
    email = this.email ?: ANONYMOUS_USER_EMAIL,
    isAnonymous = identities?.isEmpty() ?: false,
)