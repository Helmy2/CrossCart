package org.example.cross.card.core.domain.util

import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.password_requirement_length
import crosscart.composeapp.generated.resources.password_requirement_special
import crosscart.composeapp.generated.resources.password_requirement_uppercase
import org.example.cross.card.auth.domain.entity.PasswordStrength
import org.example.cross.card.auth.domain.entity.Requirement


const val ANONYMOUS_USER_NAME = "Anonymous"
const val ANONYMOUS_USER_EMAIL = ""
const val DISPLAY_NAME_KEY = "display_name"
const val PROFILE_PICTURE_KEY = "profile_picture"


fun isValidEmail(email: String): Boolean {
    val emailAddressRegex = Regex(
        """[a-zA-Z0-9+._%\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\-]{0,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{0,25})+"""
    )
    return email.matches(emailAddressRegex)
}

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

fun calculatePasswordRequirements(password: String): List<Requirement> {
    return listOf(
        Requirement(Res.string.password_requirement_length, password.length >= 8),
        Requirement(
            Res.string.password_requirement_special,
            password.any { !it.isLetterOrDigit() }),
        Requirement(
            Res.string.password_requirement_uppercase,
            password.any { it.isUpperCase() })
    )
}