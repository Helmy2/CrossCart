package org.example.cross.card.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.ic_dark_mode
import crosscart.composeapp.generated.resources.ic_light_mode
import org.example.cross.card.core.presentation.local.LocalThemeIsDark
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ThemeSwitch(modifier: Modifier = Modifier) {
    var isDark by LocalThemeIsDark.current
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
        modifier = modifier,
    )
}