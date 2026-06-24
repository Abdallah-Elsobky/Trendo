package iti.gov.trendo.data.mapper

import iti.gov.trendo.data.local.entity.NewsEntity
import iti.gov.trendo.data.remote.dto.NewsItem
import kotlin.time.Clock

fun NewsItem.toEntity(
    isFavorite: Boolean = false,
    cachedAt: Long = Clock.System.now().toEpochMilliseconds()
): NewsEntity {
    return NewsEntity(
        id = id.orEmpty(),
        author = author,
        category = category?.firstOrNull() ?: "general",
        description = description,
        image = image,
        language = language,
        published = published,
        title = title,
        url = url,
        isFavorite = isFavorite,
        cachedAt = cachedAt
    )
}

fun List<NewsItem?>.toEntityList(
    isFavorite: Boolean = false,
    cachedAt: Long = Clock.System.now().toEpochMilliseconds()
): List<NewsEntity> {
    return mapNotNull { news ->
        news?.toEntity(
            isFavorite = isFavorite,
            cachedAt = cachedAt
        )
    }
}