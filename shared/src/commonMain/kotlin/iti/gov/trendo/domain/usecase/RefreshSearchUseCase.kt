package iti.gov.trendo.domain.usecase

import iti.gov.trendo.data.remote.utils.NetworkError
import iti.gov.trendo.data.remote.utils.Result
import iti.gov.trendo.domain.repository.NewsRepository


class RefreshSearchUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(
        query: String,
        language: String? = null,
        country: String? = null,
        category: String? = null,
    ): Result<Unit, NetworkError> = repository.refreshSearch(
        query = query,
        language = language,
        country = country,
        category = category,
    )
}
