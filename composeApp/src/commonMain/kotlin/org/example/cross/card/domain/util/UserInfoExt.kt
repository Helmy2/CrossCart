package org.example.cross.card.domain.util

import io.github.jan.supabase.auth.user.UserInfo
import org.example.cross.card.domain.entity.User

fun UserInfo.toDomainUser(): User = User(
    id = this.id,
    name = userMetadata?.get("display_name")?.toString() ?: "Anonymous",
    email = this.email ?: "",
    isAnonymous = identities?.isEmpty() ?: false
)