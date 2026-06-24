package iti.gov.trendo.di

import org.koin.core.context.startKoin

val appModules = listOf(
    networkModule,
    databaseModule,
    dataSourceModule,
)

fun initKoin() {
    startKoin {
        modules(
            appModules
        )
    }
}