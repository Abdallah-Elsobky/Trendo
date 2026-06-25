package iti.gov.trendo.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.gov.trendo.domain.usecase.GetFavoriteNewsUseCase
import iti.gov.trendo.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriteNewsUseCase: GetFavoriteNewsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val state: StateFlow<FavoriteUiState> = getFavoriteNewsUseCase()
        .map { favorites -> FavoriteUiState(favorites = favorites, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoriteUiState(isLoading = true)
        )

    fun onEvent(event: FavoriteUiEvent) {
        when (event) {
            is FavoriteUiEvent.OnFavoriteToggled -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(event.articleId, event.isFavorite)
                }
            }
        }
    }
}
