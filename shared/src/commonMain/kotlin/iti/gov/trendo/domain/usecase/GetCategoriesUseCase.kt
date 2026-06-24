package iti.gov.trendo.domain.usecase

import iti.gov.trendo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Offline-first: returns a reactive [Flow] of categories from the local DB.
 * Emits an empty list until [RefreshCategoriesUseCase] has been called at least once.
 */
class GetCategoriesUseCase(private val repository: NewsRepository) {
    operator fun invoke(): Flow<List<String>> = repository.getCategories()
}
