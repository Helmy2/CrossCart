package org.example.cross.card.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun IconLabelRow(
    imageVector: ImageVector,
    text: StringResource,
    modifier: Modifier = Modifier,
    iconsSize: Dp = 16.dp,
    iconLabelSpacing: Dp = 4.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Row(modifier) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(text),
            modifier = Modifier.size(iconsSize)
        )
        Spacer(modifier = Modifier.width(iconLabelSpacing))
        Text(
            stringResource(text),
            style = textStyle
        )
    }
}