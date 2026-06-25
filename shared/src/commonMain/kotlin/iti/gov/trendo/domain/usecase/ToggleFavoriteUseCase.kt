package iti.gov.trendo.domain.usecase

import iti.gov.trendo.domain.repository.NewsRepository


class ToggleFavoriteUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(id: String, isFavorite: Boolean) =
        repository.toggleFavorite(id = id, isFavorite = isFavorite)
}
