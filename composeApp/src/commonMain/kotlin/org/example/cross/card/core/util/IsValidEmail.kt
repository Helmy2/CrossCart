package org.example.cross.card.core.util

fun isValidEmail(email: String): Boolean {
    val emailAddressRegex = Regex(
        """[a-zA-Z0-9+._%\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\-]{0,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{0,25})+"""
    )
    return email.matches(emailAddressRegex)
}