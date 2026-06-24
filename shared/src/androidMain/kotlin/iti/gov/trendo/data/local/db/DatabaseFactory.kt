package iti.gov.trendo.data.local.db

import android.content.Context
import androidx.room.Room

actual object DatabaseFactory {

    private lateinit var context: Context

    fun initialize(context: Context) {
        this.context = context
    }

    actual fun create(): AppDatabase {
        val dbFile =
            context.getDatabasePath(DATABASE_NAME)

        return Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
}