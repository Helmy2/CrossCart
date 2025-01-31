package org.example.cross.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.ic_dark_mode
import crosscart.composeapp.generated.resources.ic_light_mode
import org.example.cross.card.di.appModule
import org.example.cross.card.ui.theme.CrossCartTheme
import org.example.cross.card.ui.theme.LocalThemeIsDark
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(appModule)
            printLogger()
        },
    ) {
        CrossCartTheme {
            var isDark by LocalThemeIsDark.current
            Box(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    onClick = {
                        isDark = !isDark
                    },
                    content = {
                        Icon(
                            vectorResource(
                                if (isDark) Res.drawable.ic_light_mode
                                else Res.drawable.ic_dark_mode
                            ), contentDescription = null
                        )
                    },
                    modifier = Modifier.systemBarsPadding().padding(end = 16.dp)
                        .align(Alignment.TopEnd),
                )
            }
        }
    }
}