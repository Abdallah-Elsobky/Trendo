package iti.gov.trendo.presentation.ui.details

import iti.gov.trendo.domain.model.Article

data class DetailsUiState(
    val isLoading: Boolean = false,
    val article: Article? = null,
    val errorMessage: String? = null
)
