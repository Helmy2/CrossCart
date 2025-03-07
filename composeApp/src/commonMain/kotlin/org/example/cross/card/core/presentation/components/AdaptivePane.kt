package org.example.cross.card.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass


@Composable
fun AdaptivePane(
    modifier: Modifier = Modifier,
    firstPane: @Composable () -> Unit,
    secondPane: @Composable () -> Unit,
    spacing: Dp = 16.dp,
) {
    val size = currentWindowAdaptiveInfo()

    val cards = remember {
        movableContentOf {
            firstPane()
            Spacer(Modifier.size(spacing))
            secondPane()
        }
    }

    when (size.windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> Column(
            modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            cards()
        }

        else -> Row(
            modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            cards()
        }
    }
}