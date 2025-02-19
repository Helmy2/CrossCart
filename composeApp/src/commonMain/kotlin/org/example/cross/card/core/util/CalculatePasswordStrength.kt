package org.example.cross.card.core.util

import org.example.cross.card.features.auth.domain.entity.PasswordStrength

fun calculatePasswordStrength(password: String): PasswordStrength {
    val hasSpecialChar = password.any { !it.isLetterOrDigit() }
    val hasUppercase = password.any { it.isUpperCase() }
    val length = password.length

    return when {
        length >= 8 && hasSpecialChar && hasUppercase -> PasswordStrength.STRONG
        length >= 8 && (hasSpecialChar || hasUppercase) -> PasswordStrength.MEDIUM
        else -> PasswordStrength.WEAK
    }
}