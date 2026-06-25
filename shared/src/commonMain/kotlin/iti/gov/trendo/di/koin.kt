package iti.gov.trendo.di

import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

val appModules = listOf(
    networkModule,
    databaseModule,
    dataSourceModule,
    repositoryModule,
    useCaseModule,
    presentationModule,
)

fun initKoin() {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            modules(appModules)
        }
    }
}