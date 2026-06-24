package iti.gov.trendo.di

import iti.gov.trendo.data.repository.NewsRepositoryImpl
import iti.gov.trendo.domain.repository.NewsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<NewsRepository> {
        NewsRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
        )
    }
}
