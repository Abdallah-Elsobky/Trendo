package iti.gov.trendo.domain.usecase

import iti.gov.trendo.domain.model.Article
import iti.gov.trendo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsByIdUseCase(private val repository: NewsRepository) {
    operator fun invoke(id: String): Flow<Article?> = repository.getNewsById(id)
}
