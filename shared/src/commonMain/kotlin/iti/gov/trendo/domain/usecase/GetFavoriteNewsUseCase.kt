package iti.gov.trendo.domain.usecase

import iti.gov.trendo.domain.model.Article
import iti.gov.trendo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteNewsUseCase(private val repository: NewsRepository) {
    operator fun invoke(): Flow<List<Article>> = repository.getFavoriteNews()
}
