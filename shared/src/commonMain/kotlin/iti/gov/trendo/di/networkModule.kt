package iti.gov.trendo.di

import io.ktor.client.HttpClient
import iti.gov.trendo.data.remote.client.HttpClientFactory
import iti.gov.trendo.data.remote.datasource.NewsRemoteDataSource
import iti.gov.trendo.data.remote.datasource.NewsRemoteDataSourceImpl
import iti.gov.trendo.data.remote.service.NewsApiService
import iti.gov.trendo.data.remote.service.NewsApiServiceImpl
import org.koin.dsl.module

val networkModule = module {

    single<HttpClient> {
        HttpClientFactory.createClient()
    }

    single<NewsApiService> {
        NewsApiServiceImpl(
            httpClient = get()
        )
    }

    single<NewsRemoteDataSource> {
        NewsRemoteDataSourceImpl(
            newsApiService = get()
        )
    }
}