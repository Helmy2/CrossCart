package org.example.cross.card.product.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.cross.card.core.domain.navigation.Navigator
import org.example.cross.card.core.domain.snackbar.SnackbarManager
import org.example.cross.card.product.domain.usecase.GetCategoriesWithProductsUseCase

class HomeViewModel(
    private val getCategoriesWithProductsUseCase: GetCategoriesWithProductsUseCase,
    private val snackbarManager: SnackbarManager,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        viewModelScope.launch {
            getCategoriesWithProductsUseCase().collect { result ->
                result.fold(
                    onSuccess = { _state.value = _state.value.copy(categories = it) },
                    onFailure = { snackbarManager.showSnackbar(it.message.orEmpty()) }
                )
            }
        }
    }

    fun handleEvent(event: HomeEvent) {
        TODO()
    }


}

