package org.example.cross.card

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import org.example.cross.card.core.App
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.usecase.IsUserLongedInUseCase
import org.example.cross.card.core.presentation.CrossCartTheme
import org.example.cross.card.di.initKoin
import org.koin.compose.koinInject

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() },
) {
    val isUserLongedInUseCase = koinInject<IsUserLongedInUseCase>()

    var startDestination by remember { mutableStateOf<Destination?>(null) }

    LaunchedEffect(Unit) {
        startDestination = if (isUserLongedInUseCase()) Destination.Main else Destination.Onboarding
    }
    CrossCartTheme {
        AnimatedContent(startDestination != null) {
            if (it) {
                App(startDestination!!)
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}