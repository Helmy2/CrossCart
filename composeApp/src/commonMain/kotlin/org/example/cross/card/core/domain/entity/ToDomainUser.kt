package org.example.cross.card.core.domain.entity

import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.json.jsonPrimitive
import org.example.cross.card.core.util.ANONYMOUS_USER_EMAIL
import org.example.cross.card.core.util.ANONYMOUS_USER_NAME
import org.example.cross.card.core.util.DISPLAY_NAME_KEY
import org.example.cross.card.core.util.PROFILE_PICTURE_KEY

fun UserInfo.toDomainUser(): User = User(
    id = this.id,
    name = userMetadata?.get(DISPLAY_NAME_KEY)?.jsonPrimitive?.content ?: ANONYMOUS_USER_NAME,
    profilePicture = userMetadata?.get(PROFILE_PICTURE_KEY)?.jsonPrimitive?.content,
    email = this.email ?: ANONYMOUS_USER_EMAIL,
    isAnonymous = identities?.isEmpty() ?: false,
)