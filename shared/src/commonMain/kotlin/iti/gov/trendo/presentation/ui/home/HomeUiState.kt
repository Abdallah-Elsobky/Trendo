package iti.gov.trendo.presentation.ui.home

import iti.gov.trendo.domain.model.Article

data class HomeUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val categories: List<String> = emptyList(),
    val regions: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val selectedRegion: String? = null,
    val searchQuery: String = "",
    val errorMessage: String? = null
)
