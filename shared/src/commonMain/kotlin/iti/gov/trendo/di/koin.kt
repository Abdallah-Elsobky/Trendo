package iti.gov.trendo.di

import org.koin.core.context.startKoin

val appModules = listOf(
    networkModule,
)

fun initKoin() {
    startKoin {
        modules(
            appModules
        )
    }
}