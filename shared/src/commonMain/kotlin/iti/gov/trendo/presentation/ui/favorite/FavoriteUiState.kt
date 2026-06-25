package iti.gov.trendo.presentation.ui.favorite

import iti.gov.trendo.domain.model.Article

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val favorites: List<Article> = emptyList(),
    val errorMessage: String? = null
)
