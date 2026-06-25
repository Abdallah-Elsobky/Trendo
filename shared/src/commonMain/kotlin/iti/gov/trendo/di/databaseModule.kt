package iti.gov.trendo.di

import iti.gov.trendo.data.local.db.AppDatabase
import iti.gov.trendo.data.local.db.DatabaseFactory
import org.koin.dsl.module

val databaseModule = module {

    single<AppDatabase> {
        DatabaseFactory.create()
    }

    single {
        get<AppDatabase>().newsDao()
    }

    single {
        get<AppDatabase>().metadataDao()
    }
}