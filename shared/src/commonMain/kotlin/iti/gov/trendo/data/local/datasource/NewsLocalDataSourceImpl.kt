package iti.gov.trendo.data.local.datasource

import iti.gov.trendo.data.local.dao.NewsDao
import iti.gov.trendo.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(val newsDao: NewsDao) : NewsLocalDataSource {

    override fun getNews(): Flow<List<NewsEntity>> =
        newsDao.observeCachedNews()

    override fun getFilteredNews(
        category: String?,
        language: String?,
        region: String?,
    ): Flow<List<NewsEntity>> =
        newsDao.observeFilteredNews(
            category = category,
            language = language,
            region = region,
        )

    override fun searchNews(
        query: String,
        category: String?,
        language: String?,
        region: String?,
    ): Flow<List<NewsEntity>> =
        newsDao.searchCachedNews(
            query = query,
            category = category,
            language = language,
            region = region,
        )

    override fun getNewsById(id: String): Flow<NewsEntity?> =
        newsDao.getNewsById(id)

    override fun getFavoriteNews(): Flow<List<NewsEntity>> =
        newsDao.observeFavoriteNews()

    override suspend fun saveNews(news: List<NewsEntity>) =
        newsDao.insertNews(news)

    override suspend fun addToFavorite(id: String) =
        newsDao.addToFavorite(id)

    override suspend fun removeFromFavorite(id: String) =
        newsDao.removeFromFavorite(id)

    override suspend fun clearExpiredNews(expirationTime: Long) =
        newsDao.deleteExpiredNews(expirationTime)

    override suspend fun clearNews() =
        newsDao.clearNonFavoriteNews()
}
