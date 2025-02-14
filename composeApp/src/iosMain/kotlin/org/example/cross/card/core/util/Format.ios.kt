package org.example.cross.card.core.util

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun String.format(value: Any): String {
    return NSString.stringWithFormat(this, value)
}