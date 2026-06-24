package iti.gov.trendo.domain.usecase

import iti.gov.trendo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetRegionsUseCase(private val repository: NewsRepository) {
    operator fun invoke(): Flow<List<String>> = repository.getRegions()
}
