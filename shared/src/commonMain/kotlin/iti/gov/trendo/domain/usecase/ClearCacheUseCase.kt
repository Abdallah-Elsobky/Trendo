package iti.gov.trendo.domain.usecase

import iti.gov.trendo.domain.repository.NewsRepository

class ClearCacheUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke() = repository.clearCache()
}
