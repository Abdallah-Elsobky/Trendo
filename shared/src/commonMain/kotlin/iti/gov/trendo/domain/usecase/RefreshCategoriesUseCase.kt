package iti.gov.trendo.domain.usecase

import iti.gov.trendo.data.remote.utils.NetworkError
import iti.gov.trendo.data.remote.utils.Result
import iti.gov.trendo.domain.repository.NewsRepository

/**
 * Fetches categories from the remote API and saves them to the local DB.
 * The [GetCategoriesUseCase] Flow re-emits automatically on success.
 */
class RefreshCategoriesUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(): Result<Unit, NetworkError> =
        repository.refreshCategories()
}
