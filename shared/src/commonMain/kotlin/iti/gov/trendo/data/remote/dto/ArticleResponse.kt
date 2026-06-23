package iti.gov.trendo.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    @SerialName("news")
    val news: List<NewsItem?>?,
    @SerialName("page")
    val page: Int?,
    @SerialName("status")
    val status: String?
)