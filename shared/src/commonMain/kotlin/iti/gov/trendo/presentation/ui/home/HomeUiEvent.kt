package iti.gov.trendo.presentation.ui.home

sealed interface HomeUiEvent {
    data class OnSearchQueryChanged(val query: String) : HomeUiEvent
    data class OnCategorySelected(val category: String?) : HomeUiEvent
    data class OnRegionSelected(val region: String?) : HomeUiEvent
    data class OnFavoriteToggled(val articleId: String, val isFavorite: Boolean) : HomeUiEvent
    data object OnRefresh : HomeUiEvent
    data object OnClearError : HomeUiEvent
}
