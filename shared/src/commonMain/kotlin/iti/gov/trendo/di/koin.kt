package iti.gov.trendo.di

import org.koin.core.context.startKoin

val appModules = listOf(
    networkModule,
    databaseModule,
    dataSourceModule,
    repositoryModule,
    useCaseModule,
)

fun initKoin() {
    startKoin {
        modules(
            appModules
        )
    }
}