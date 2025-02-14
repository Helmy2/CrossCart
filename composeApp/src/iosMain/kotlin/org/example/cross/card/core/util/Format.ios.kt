package org.example.cross.card.core.util

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun Float.format(numberOfDecimalPlaces: Int): String {
    return NSString.stringWithFormat("%.${numberOfDecimalPlaces}f", this)
}