package iti.gov.trendo.data.local.datasource

import iti.gov.trendo.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {

    fun getNews(): Flow<List<NewsEntity>>

    fun getFilteredNews(
        category: String? = null,
        language: String? = null,
        region: String? = null,
    ): Flow<List<NewsEntity>>

    fun searchNews(
        query: String,
        category: String? = null,
        language: String? = null,
        region: String? = null,
    ): Flow<List<NewsEntity>>

    fun getNewsById(id: String): Flow<NewsEntity?>

    fun getFavoriteNews(): Flow<List<NewsEntity>>

    suspend fun saveNews(news: List<NewsEntity>)

    suspend fun addToFavorite(id: String)

    suspend fun removeFromFavorite(id: String)

    suspend fun clearExpiredNews(expirationTime: Long)

    suspend fun clearNews()
}
