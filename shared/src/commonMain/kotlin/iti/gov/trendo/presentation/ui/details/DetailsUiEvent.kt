package iti.gov.trendo.presentation.ui.details

sealed interface DetailsUiEvent {
    data class Init(val articleId: String) : DetailsUiEvent
    data object OnFavoriteToggled : DetailsUiEvent
    data object OnClearError : DetailsUiEvent
}
