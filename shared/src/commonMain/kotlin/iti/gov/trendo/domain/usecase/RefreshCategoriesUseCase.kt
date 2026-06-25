package iti.gov.trendo.domain.usecase

import iti.gov.trendo.data.remote.utils.NetworkError
import iti.gov.trendo.data.remote.utils.Result
import iti.gov.trendo.domain.repository.NewsRepository

class RefreshCategoriesUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(): Result<Unit, NetworkError> =
        repository.refreshCategories()
}
