package org.example.cross.card.di

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.core.domain.usecase.IsUserLongedInUseCase
import org.example.cross.card.core.presentation.navigation.NavigatorImpl
import org.example.cross.card.core.presentation.snackbar.SnackbarManagerImpl
import org.koin.dsl.module

val coreModule = module {

    single<Navigator> { (navController: NavHostController) ->
        NavigatorImpl(navController)
    }
    single<SnackbarManager> { (snackbarHostState: SnackbarHostState) ->
        SnackbarManagerImpl(snackbarHostState)
    }

    factory { IsUserLongedInUseCase(get()) }

}