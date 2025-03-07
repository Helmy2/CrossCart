package org.example.cross.card

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.cross.card.core.App
import org.example.cross.card.core.domain.navigation.Destination
import org.example.cross.card.core.domain.usecase.IsUserLongedInUseCase
import org.example.cross.card.core.presentation.CrossCartTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        val isUserLongedInUseCase: IsUserLongedInUseCase by inject()
        val startDestination: MutableStateFlow<Destination?> = MutableStateFlow(null)

        lifecycleScope.launch {
            isUserLongedInUseCase().also {
                startDestination.value = if (it) Destination.Main else Destination.Onboarding
            }
        }

        splashScreen.setKeepOnScreenCondition {
            startDestination.value == null
        }

        setContent {
            val state by startDestination.collectAsStateWithLifecycle()
            CrossCartTheme {
                state?.let {
                    App(it)
                }
            }
        }
    }
}