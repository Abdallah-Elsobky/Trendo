package iti.gov.trendo.data.remote.service

import iti.gov.trendo.data.remote.dto.ArticleResponse
import iti.gov.trendo.data.remote.dto.CategoryResponse
import iti.gov.trendo.data.remote.dto.RegionResponse
import iti.gov.trendo.data.remote.utils.NetworkError
import iti.gov.trendo.data.remote.utils.Result

interface NewsApiService {
    suspend fun getArticles(
        category: String? = null,
        language: String? = null,
        region: String? = null,
    ): Result<ArticleResponse, NetworkError>

    suspend fun searchArticles(
        keywords: String,
        language: String? = null,
        country: String? = null,
        category: String? = null
    ): Result<ArticleResponse, NetworkError>

    suspend fun getCategories(): Result<CategoryResponse, NetworkError>

    suspend fun getRegions(): Result<RegionResponse, NetworkError>
}