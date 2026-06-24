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

    /**
     * Reactive stream of categories from the local DB.
     * Emits an empty list until [refreshCategories] populates the cache.
     */
    fun getCategories(): Flow<List<String>>

    /**
     * Fetches categories from the API and saves to the local DB.
     * The [getCategories] Flow re-emits automatically on success.
     */
    suspend fun refreshCategories(): Result<Unit, NetworkError>

    /**
     * Reactive stream of regions from the local DB.
     * Emits an empty list until [refreshRegions] populates the cache.
     */
    fun getRegions(): Flow<List<String>>

    /**
     * Fetches regions from the API and saves to the local DB.
     * The [getRegions] Flow re-emits automatically on success.
     */
    suspend fun refreshRegions(): Result<Unit, NetworkError>

    suspend fun clearExpiredCache(expirationTime: Long)

    suspend fun clearCache()
}

