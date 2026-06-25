package iti.gov.trendo.presentation.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.gov.trendo.domain.usecase.GetNewsByIdUseCase
import iti.gov.trendo.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getNewsByIdUseCase: GetNewsByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsUiState())
    val state = _state.asStateFlow()

    private var articleId: String? = null
    private var job: Job? = null

    fun onEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.Init -> {
                if (articleId == event.articleId) return
                articleId = event.articleId
                job?.cancel()
                job = viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    getNewsByIdUseCase(event.articleId).collect { article ->
                        _state.update { it.copy(article = article, isLoading = false) }
                    }
                }
            }
            is DetailsUiEvent.OnFavoriteToggled -> {
                val currentArticle = _state.value.article ?: return
                viewModelScope.launch {
                    toggleFavoriteUseCase(currentArticle.id, !currentArticle.isFavorite)
                }
            }
            DetailsUiEvent.OnClearError -> {
                _state.update { it.copy(errorMessage = null) }
            }
        }
    }
}
