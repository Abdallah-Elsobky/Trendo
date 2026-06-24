package iti.gov.trendo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import iti.gov.trendo.data.local.entity.CategoryEntity
import iti.gov.trendo.data.local.entity.RegionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MetadataDao {

    // ─── Categories ───────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun observeCategories(): Flow<List<CategoryEntity>>

    @Query("DELETE FROM categories")
    suspend fun clearCategories()

    // ─── Regions ──────────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegions(regions: List<RegionEntity>)

    @Query("SELECT * FROM regions ORDER BY name ASC")
    fun observeRegions(): Flow<List<RegionEntity>>

    @Query("DELETE FROM regions")
    suspend fun clearRegions()
}
