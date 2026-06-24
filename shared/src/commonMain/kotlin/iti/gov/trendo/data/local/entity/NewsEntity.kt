package iti.gov.trendo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    val id: String,

    val author: String?,
    val category: String?,
    val description: String?,
    val image: String?,
    val language: String?,
    val published: String?,
    val title: String?,
    val url: String?,

    val isFavorite: Boolean = false,
    val cachedAt: Long
)