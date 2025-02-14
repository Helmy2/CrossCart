package org.example.cross.card.core.util

actual fun Float.format(numberOfDecimalPlaces: Int): String {
    return String.format("%.${numberOfDecimalPlaces}f", this)
}