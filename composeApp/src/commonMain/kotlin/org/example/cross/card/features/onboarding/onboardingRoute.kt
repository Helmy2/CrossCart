package org.example.cross.card.features.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.example.cross.card.core.domain.navigation.Destination

fun NavGraphBuilder.onboardingRoute(
    onCompleted: () -> Unit
) {
    composable<Destination.Onboarding> {
        OnboardingScreen(
            onCompleted = onCompleted
        )
    }
}
