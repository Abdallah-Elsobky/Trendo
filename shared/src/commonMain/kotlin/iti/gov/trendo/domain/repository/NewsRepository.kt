package iti.gov.trendo.domain.repository

import iti.gov.trendo.data.remote.utils.NetworkError
import iti.gov.trendo.data.remote.utils.Result
import iti.gov.trendo.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(
        category: String? = null,
        language: String? = null,
        region: String? = null,
    ): Flow<List<Article>>

    suspend fun refreshNews(
        category: String? = null,
        language: String? = null,
        region: String? = null,
    ): Result<Unit, NetworkError>

    fun searchNews(
        query: String,
        category: String? = null,
        language: String? = null,
        region: String? = null,
    ): Flow<List<Article>>

    suspend fun refreshSearch(
        query: String,
        language: String? = null,
        country: String? = null,
        category: String? = null,
    ): Result<Unit, NetworkError>

    fun getNewsById(id: String): Flow<Article?>

    fun getFavoriteNews(): Flow<List<Article>>

    suspend fun toggleFavorite(id: String, isFavorite: Boolean)

    suspend fun getCategories(): Result<List<String>, NetworkError>

    suspend fun getRegions(): Result<List<String>, NetworkError>

    suspend fun clearExpiredCache(expirationTime: Long)

    suspend fun clearCache()
}

