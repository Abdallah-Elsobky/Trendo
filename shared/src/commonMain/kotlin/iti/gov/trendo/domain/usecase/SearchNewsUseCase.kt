package iti.gov.trendo.domain.usecase

import iti.gov.trendo.domain.model.Article
import iti.gov.trendo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Offline-first search: queries the local DB immediately, returning a reactive
 * [Flow] that matches [query] across title, description, and author.
 *
 * Works without internet. Combine with [RefreshSearchUseCase] to enrich
 * the cache with fresh API results.
 */
class SearchNewsUseCase(private val repository: NewsRepository) {
    operator fun invoke(
        query: String,
        category: String? = null,
        language: String? = null,
        region: String? = null,
    ): Flow<List<Article>> = repository.searchNews(
        query = query,
        category = category,
        language = language,
        region = region,
    )
}
