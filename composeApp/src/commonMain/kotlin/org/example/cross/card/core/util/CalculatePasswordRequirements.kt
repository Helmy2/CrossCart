package org.example.cross.card.core.util

import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.password_requirement_length
import crosscart.composeapp.generated.resources.password_requirement_special
import crosscart.composeapp.generated.resources.password_requirement_uppercase
import org.example.cross.card.auth.domain.entity.Requirement

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