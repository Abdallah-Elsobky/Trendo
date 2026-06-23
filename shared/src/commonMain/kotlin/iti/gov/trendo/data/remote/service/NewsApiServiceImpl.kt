package iti.gov.trendo.data.remote.service

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import iti.gov.trendo.data.remote.dto.ArticleResponse
import iti.gov.trendo.data.remote.dto.CategoryResponse
import iti.gov.trendo.data.remote.dto.RegionResponse
import iti.gov.trendo.data.remote.utils.NetworkError
import iti.gov.trendo.data.remote.utils.Result
import iti.gov.trendo.data.remote.utils.safeApiCall

class NewsApiServiceImpl(private val httpClient: HttpClient) : NewsApiService {
    override suspend fun getArticles(
        category: String?,
        language: String?,
        region: String?,
    ): Result<ArticleResponse, NetworkError> =
        safeApiCall {
            httpClient.get("latest-news") {
                category?.let {
                    parameter("category", it)
                }

                language?.let {
                    parameter("language", it)
                }

                region?.let {
                    parameter("region", it)
                }
            }
        }

    override suspend fun searchArticles(
        keywords: String,
        language: String?,
        country: String?,
        category: String?
    ): Result<ArticleResponse, NetworkError> =
        safeApiCall {
            httpClient.get("search") {
                parameter("keywords", keywords)

                language?.let { parameter("language", it) }
                country?.let { parameter("country", it) }
                category?.let { parameter("category", it) }
            }
        }

    override suspend fun getCategories(): Result<CategoryResponse, NetworkError> =
        safeApiCall {
            httpClient.get("available/categories")
        }

    override suspend fun getRegions(): Result<RegionResponse, NetworkError> =
        safeApiCall {
            httpClient.get("available/regions")
        }

}
