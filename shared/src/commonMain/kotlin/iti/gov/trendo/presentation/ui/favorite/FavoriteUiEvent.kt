package iti.gov.trendo.presentation.ui.favorite

sealed interface FavoriteUiEvent {
    data class OnFavoriteToggled(val articleId: String, val isFavorite: Boolean) : FavoriteUiEvent
}
