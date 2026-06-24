package iti.gov.trendo.data.local.datasource

import iti.gov.trendo.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {

    suspend fun getNews(): Flow<List<NewsEntity>>

    suspend fun saveNews(news: List<NewsEntity>)

    suspend fun getFavoriteNews(): Flow<List<NewsEntity>>

    suspend fun getNewsById(id: String): Flow<NewsEntity?>

    suspend fun addToFavorite(id: String, )

    suspend fun removeFromFavorite(id: String)

    suspend fun clearNews()
}