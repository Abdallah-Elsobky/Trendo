package iti.gov.trendo.data.local.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import iti.gov.trendo.data.local.dao.NewsDao
import iti.gov.trendo.data.local.entity.NewsEntity

@Database(
    entities = [
        NewsEntity::class,
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}