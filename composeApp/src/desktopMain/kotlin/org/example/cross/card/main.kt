package org.example.cross.card

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.cross.card.core.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CrossCart",
    ) {
        App()
    }
}