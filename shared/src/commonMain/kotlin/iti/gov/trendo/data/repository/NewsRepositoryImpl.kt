package iti.gov.trendo.data.repository

import iti.gov.trendo.data.local.datasource.NewsLocalDataSource
import iti.gov.trendo.data.mapper.toArticle
import iti.gov.trendo.data.mapper.toArticleList
import iti.gov.trendo.data.mapper.toEntityList
import iti.gov.trendo.data.remote.datasource.NewsRemoteDataSource
import iti.gov.trendo.data.remote.utils.NetworkError
import iti.gov.trendo.data.remote.utils.Result
import iti.gov.trendo.data.remote.utils.map
import iti.gov.trendo.data.remote.utils.onSuccess
import iti.gov.trendo.domain.model.Article
import iti.gov.trendo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource,
) : NewsRepository {

    companion object {
        private const val CACHE_EXPIRY_MS = 3 * 24 * 60 * 60 * 1_000L
    }

    override fun getNews(
        category: String?,
        language: String?,
        region: String?,
    ): Flow<List<Article>> {
        val hasFilter = category != null || language != null || region != null
        return if (hasFilter) {
            localDataSource.getFilteredNews(
                category = category,
                language = language,
                region = region,
            )
        } else {
            localDataSource.getNews()
        }.map { entities -> entities.toArticleList() }
    }

    override suspend fun refreshNews(
        category: String?,
        language: String?,
        region: String?,
    ): Result<Unit, NetworkError> =
        remoteDataSource
            .getArticles(category = category, language = language, region = region)
            .onSuccess { response ->
                val entities = response.news?.toEntityList() ?: emptyList()
                if (entities.isNotEmpty()) {
                    val expiry = Clock.System.now().toEpochMilliseconds() - CACHE_EXPIRY_MS
                    localDataSource.clearExpiredNews(expiry)
                    localDataSource.saveNews(entities)
                }
            }
            .map { }

    override fun searchNews(
        query: String,
        category: String?,
        language: String?,
        region: String?,
    ): Flow<List<Article>> =
        localDataSource.searchNews(
            query = query,
            category = category,
            language = language,
            region = region,
        ).map { entities -> entities.toArticleList() }

    override suspend fun refreshSearch(
        query: String,
        language: String?,
        country: String?,
        category: String?,
    ): Result<Unit, NetworkError> =
        remoteDataSource
            .searchArticles(
                keywords = query,
                language = language,
                country = country,
                category = category,
            )
            .onSuccess { response ->
                val entities = response.news?.toEntityList() ?: emptyList()
                if (entities.isNotEmpty()) {
                    localDataSource.saveNews(entities)
                }
            }
            .map { }

    override fun getNewsById(id: String): Flow<Article?> =
        localDataSource.getNewsById(id).map { entity -> entity?.toArticle() }

    override fun getFavoriteNews(): Flow<List<Article>> =
        localDataSource.getFavoriteNews().map { entities -> entities.toArticleList() }

    override suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        if (isFavorite) localDataSource.addToFavorite(id)
        else localDataSource.removeFromFavorite(id)
    }

    override suspend fun getCategories(): Result<List<String>, NetworkError> =
        remoteDataSource.getCategories().map { response -> response.categories }


    override suspend fun getRegions(): Result<List<String>, NetworkError> =
        remoteDataSource.getRegions().map { response -> response.regions }


    override suspend fun clearExpiredCache(expirationTime: Long) =
        localDataSource.clearExpiredNews(expirationTime)

    override suspend fun clearCache() =
        localDataSource.clearNews()
}
