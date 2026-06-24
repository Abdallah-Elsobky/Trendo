package iti.gov.trendo.data.local.datasource

import iti.gov.trendo.data.local.dao.MetadataDao
import iti.gov.trendo.data.local.dao.NewsDao
import iti.gov.trendo.data.local.entity.CategoryEntity
import iti.gov.trendo.data.local.entity.NewsEntity
import iti.gov.trendo.data.local.entity.RegionEntity
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val newsDao: NewsDao,
    private val metadataDao: MetadataDao,
) : NewsLocalDataSource {

    // ─── News ─────────────────────────────────────────────────────────────────

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

    // ─── Categories ───────────────────────────────────────────────────────────

    override fun getCategories(): Flow<List<CategoryEntity>> =
        metadataDao.observeCategories()

    override suspend fun saveCategories(categories: List<CategoryEntity>) =
        metadataDao.insertCategories(categories)

    // ─── Regions ──────────────────────────────────────────────────────────────

    override fun getRegions(): Flow<List<RegionEntity>> =
        metadataDao.observeRegions()

    override suspend fun saveRegions(regions: List<RegionEntity>) =
        metadataDao.insertRegions(regions)
}

