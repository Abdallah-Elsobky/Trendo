package iti.gov.trendo.di

import iti.gov.trendo.data.local.datasource.NewsLocalDataSource
import iti.gov.trendo.data.local.datasource.NewsLocalDataSourceImpl
import iti.gov.trendo.data.remote.datasource.NewsRemoteDataSource
import iti.gov.trendo.data.remote.datasource.NewsRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<NewsRemoteDataSource> {
        NewsRemoteDataSourceImpl(
            newsApiService = get()
        )
    }

    single<NewsLocalDataSource> {
        NewsLocalDataSourceImpl(
            newsDao = get()
        )
    }
}