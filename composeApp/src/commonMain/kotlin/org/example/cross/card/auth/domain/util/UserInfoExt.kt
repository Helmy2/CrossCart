package org.example.cross.card.auth.domain.util

import io.github.jan.supabase.auth.user.UserInfo
import org.example.cross.card.auth.domain.entity.User

fun UserInfo.toDomainUser(): User = User(
    id = this.id,
    name = userMetadata?.get(DISPLAY_NAME_KEY)?.toString() ?: ANONYMOUS_USER_NAME,
    email = this.email ?: ANONYMOUS_USER_EMAIL,
    isAnonymous = identities?.isEmpty() ?: false
)