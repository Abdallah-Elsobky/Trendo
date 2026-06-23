package iti.gov.trendo.data.remote.datasource

import iti.gov.trendo.data.remote.dto.ArticleResponse
import iti.gov.trendo.data.remote.dto.CategoryResponse
import iti.gov.trendo.data.remote.dto.RegionResponse
import iti.gov.trendo.data.remote.service.NewsApiService
import iti.gov.trendo.data.remote.utils.NetworkError
import iti.gov.trendo.data.remote.utils.Result

class NewsRemoteDataSourceImpl(val newsApiService: NewsApiService) : NewsRemoteDataSource {
    override suspend fun getArticles(
        category: String?,
        language: String?,
        region: String?
    ): Result<ArticleResponse, NetworkError> =
        newsApiService.getArticles(
            category = category, language = language, region = region
        )

    override suspend fun searchArticles(
        keywords: String,
        language: String?,
        country: String?,
        category: String?
    ): Result<ArticleResponse, NetworkError> =
        newsApiService.searchArticles(
            keywords = keywords,
            language = language,
            country = country,
            category = category
        )

    override suspend fun getCategories(): Result<CategoryResponse, NetworkError> =
        newsApiService.getCategories()

    override suspend fun getRegions(): Result<RegionResponse, NetworkError> =
        newsApiService.getRegions()
}