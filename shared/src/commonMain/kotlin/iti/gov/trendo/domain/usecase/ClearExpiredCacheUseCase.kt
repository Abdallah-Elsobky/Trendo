package iti.gov.trendo.domain.usecase

import iti.gov.trendo.domain.repository.NewsRepository
import kotlin.time.Clock

class ClearExpiredCacheUseCase(private val repository: NewsRepository) {
    companion object {
        const val DEFAULT_EXPIRY_MS = 3 * 24 * 60 * 60 * 1_000L
    }

    suspend operator fun invoke(
        expirationTime: Long = Clock.System.now().toEpochMilliseconds() - DEFAULT_EXPIRY_MS
    ) = repository.clearExpiredCache(expirationTime)
}
