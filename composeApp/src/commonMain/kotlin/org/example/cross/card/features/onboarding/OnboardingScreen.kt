package org.example.cross.card.features.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.cross.card.features.onboarding.components.BottomSection
import org.example.cross.card.features.onboarding.entity.OnBoardingData
import org.example.cross.card.features.onboarding.entity.onBoardingPages
import org.jetbrains.compose.resources.painterResource


@Composable
fun OnboardingScreen(
    items: List<OnBoardingData> = onBoardingPages,
    onCompleted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { items.size },
        initialPage = 0
    )

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.weight(1f).padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(items[page].backImage),
                            contentDescription = null,
                        )
                        Icon(
                            painter = painterResource(items[page].frontImage),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = items[page].title,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = items[page].description,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            BottomSection(
                numberOfIndicators = items.size,
                currentIndex = pagerState.currentPage,
            ) {
                if (pagerState.currentPage + 1 < items.size) scope.launch {
                    pagerState.scrollToPage(pagerState.currentPage + 1)
                } else {
                    onCompleted()
                }
            }
        }
    }
}

