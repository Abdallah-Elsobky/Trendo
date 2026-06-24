package iti.gov.trendo.data.local.db

import androidx.room.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor :
    RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}