package iti.gov.trendo.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsItem(
    @SerialName("author")
    val author: String?,
    @SerialName("category")
    val category: List<String?>?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("language")
    val language: String?,
    @SerialName("published")
    val published: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("url")
    val url: String?
)