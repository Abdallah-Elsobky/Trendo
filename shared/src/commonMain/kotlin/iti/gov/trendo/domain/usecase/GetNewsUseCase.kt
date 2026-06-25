package iti.gov.trendo.domain.usecase

import iti.gov.trendo.domain.model.Article
import iti.gov.trendo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(private val repository: NewsRepository) {
    operator fun invoke(
        category: String? = null,
        language: String? = null,
        region: String? = null,
    ): Flow<List<Article>> = repository.getNews(
        category = category,
        language = language,
        region = region,
    )
}
