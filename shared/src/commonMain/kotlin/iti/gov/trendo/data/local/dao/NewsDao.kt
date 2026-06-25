package iti.gov.trendo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import iti.gov.trendo.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity)

    @Query("SELECT * FROM news ORDER BY cachedAt DESC")
    fun observeCachedNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE isFavorite = 1")
    fun observeFavoriteNews(): Flow<List<NewsEntity>>

    @Query("UPDATE news SET isFavorite = :isFavorite WHERE id = :newsId")
    suspend fun updateFavorite(
        newsId: String,
        isFavorite: Boolean
    )

    suspend fun addToFavorite(newsId: String) {
        updateFavorite(newsId, true)
    }

    suspend fun removeFromFavorite(newsId: String) {
        updateFavorite(newsId, false)
    }


    @Query("SELECT * FROM news WHERE id = :newsId")
    fun getNewsById(newsId: String): Flow<NewsEntity?>

    @Query("DELETE FROM news")
    suspend fun clearCache()

    @Query("DELETE FROM news WHERE isFavorite = 0")
    suspend fun clearNonFavoriteNews()


    @Query(
        """
        DELETE FROM news
        WHERE cachedAt < :expirationTime
        AND isFavorite = 0
        """
    )
    suspend fun deleteExpiredNews(
        expirationTime: Long
    )

    @Query(
        """
        SELECT * FROM news
        WHERE (:category IS NULL OR category = :category)
          AND (:language IS NULL OR language = :language)
          AND (:region   IS NULL OR url LIKE '%' || :region || '%')
        ORDER BY cachedAt DESC
        """
    )
    fun observeFilteredNews(
        category: String?,
        language: String?,
        region: String?,
    ): Flow<List<NewsEntity>>


    @Query(
        """
        SELECT * FROM news
        WHERE (title       LIKE '%' || :query || '%'
            OR description LIKE '%' || :query || '%'
            OR author      LIKE '%' || :query || '%')
          AND (:category IS NULL OR category = :category)
          AND (:language IS NULL OR language = :language)
          AND (:region   IS NULL OR url LIKE '%' || :region || '%')
        ORDER BY cachedAt DESC
        """
    )
    fun searchCachedNews(
        query: String,
        category: String?,
        language: String?,
        region: String?,
    ): Flow<List<NewsEntity>>
}