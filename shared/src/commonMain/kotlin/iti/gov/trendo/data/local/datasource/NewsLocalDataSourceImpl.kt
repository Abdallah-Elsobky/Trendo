package iti.gov.trendo.data.local.datasource

import iti.gov.trendo.data.local.dao.NewsDao
import iti.gov.trendo.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(val newsDao: NewsDao) : NewsLocalDataSource {
    override suspend fun getNews(): Flow<List<NewsEntity>> = newsDao.observeCachedNews()

    override suspend fun saveNews(news: List<NewsEntity>) = newsDao.insertNews(news)

    override suspend fun getFavoriteNews(): Flow<List<NewsEntity>> = newsDao.observeFavoriteNews()

    override suspend fun getNewsById(id: String): Flow<NewsEntity?> = newsDao.getNewsById(id)

    override suspend fun addToFavorite(id: String) = newsDao.addToFavorite(id)

    override suspend fun removeFromFavorite(id: String) = newsDao.removeFromFavorite(id)

    override suspend fun clearNews() = newsDao.clearCache()
}