package iti.gov.trendo.domain.usecase

import iti.gov.trendo.data.remote.utils.NetworkError
import iti.gov.trendo.data.remote.utils.Result
import iti.gov.trendo.domain.repository.NewsRepository

class RefreshNewsUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(
        category: String? = null,
        language: String? = null,
        region: String? = null,
    ): Result<Unit, NetworkError> = repository.refreshNews(
        category = category,
        language = language,
        region = region,
    )
}
