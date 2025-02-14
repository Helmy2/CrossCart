package org.example.cross.card.core.util

actual fun String.format(value: Any): String {
    return String.format(this, value)
}