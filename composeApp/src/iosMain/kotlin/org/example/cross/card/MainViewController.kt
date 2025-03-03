package org.example.cross.card

import androidx.compose.ui.window.ComposeUIViewController
import org.example.cross.card.core.App
import org.example.cross.card.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    },
) { App() }