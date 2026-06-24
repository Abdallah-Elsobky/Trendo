package iti.gov.trendo.data.local.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import iti.gov.trendo.data.local.dao.MetadataDao
import iti.gov.trendo.data.local.dao.NewsDao
import iti.gov.trendo.data.local.entity.CategoryEntity
import iti.gov.trendo.data.local.entity.NewsEntity
import iti.gov.trendo.data.local.entity.RegionEntity

@Database(
    entities = [
        NewsEntity::class,
        CategoryEntity::class,
        RegionEntity::class,
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun metadataDao(): MetadataDao
}