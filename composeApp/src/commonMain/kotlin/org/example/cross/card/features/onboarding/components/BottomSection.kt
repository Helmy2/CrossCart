package org.example.cross.card.features.onboarding.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun BottomSection(
    numberOfIndicators: Int,
    currentIndex: Int,
    isLastPage: Boolean = numberOfIndicators == currentIndex + 1,
    onNextButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Indicators(numberOfIndicators, currentIndex)

        FloatingActionButton(
            onClick = onNextButtonClick,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(15.dp))
        ) {
            Icon(
                if (isLastPage) Icons.Default.Check else
                    Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = "Localized description"
            )
        }
    }
}