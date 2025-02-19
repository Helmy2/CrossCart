package org.example.cross.card.features.auth.domain.entity

enum class PasswordStrength(val strengthValue: Float) {
    WEAK(0.33f),
    MEDIUM(0.66f),
    STRONG(1f)
}