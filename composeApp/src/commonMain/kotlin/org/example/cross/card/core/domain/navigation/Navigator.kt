package org.example.cross.card.core.domain.navigation

import androidx.navigation.NavHostController

interface Navigator {
    val navController: NavHostController
    fun navigateBack()
    fun navigate(route: Destination)
    fun navigateAsStart(route: Destination)
}