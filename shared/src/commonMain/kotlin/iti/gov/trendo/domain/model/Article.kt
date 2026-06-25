package iti.gov.trendo.domain.model

data class Article(
    val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val category: String,
    val author: String?,
    val publishedAt: String?,
    val isFavorite: Boolean,
    val url: String? = null
)