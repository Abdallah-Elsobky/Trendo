package iti.gov.trendo.data.local.db

import androidx.room.Room
import platform.Foundation.NSHomeDirectory

actual object DatabaseFactory {

    actual fun create(): AppDatabase {
        val dbFile =
            NSHomeDirectory() +
                    "/Documents/$DATABASE_NAME"

        return Room.databaseBuilder<AppDatabase>(
            name = dbFile
        ).build()
    }
}