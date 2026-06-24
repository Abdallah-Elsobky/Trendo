package iti.gov.trendo.domain.usecase

import iti.gov.trendo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Offline-first: returns a reactive [Flow] of regions from the local DB.
 * Emits an empty list until [RefreshRegionsUseCase] has been called at least once.
 */
class GetRegionsUseCase(private val repository: NewsRepository) {
    operator fun invoke(): Flow<List<String>> = repository.getRegions()
}
