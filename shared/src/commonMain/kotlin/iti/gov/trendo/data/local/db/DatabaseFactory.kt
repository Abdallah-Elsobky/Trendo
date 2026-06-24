package iti.gov.trendo.data.local.db

expect object DatabaseFactory {
    fun create(): AppDatabase
}