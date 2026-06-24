package iti.gov.trendo.di

import iti.gov.trendo.domain.usecase.ClearCacheUseCase
import iti.gov.trendo.domain.usecase.ClearExpiredCacheUseCase
import iti.gov.trendo.domain.usecase.GetCategoriesUseCase
import iti.gov.trendo.domain.usecase.GetFavoriteNewsUseCase
import iti.gov.trendo.domain.usecase.GetNewsByIdUseCase
import iti.gov.trendo.domain.usecase.GetNewsUseCase
import iti.gov.trendo.domain.usecase.GetRegionsUseCase
import iti.gov.trendo.domain.usecase.RefreshCategoriesUseCase
import iti.gov.trendo.domain.usecase.RefreshNewsUseCase
import iti.gov.trendo.domain.usecase.RefreshRegionsUseCase
import iti.gov.trendo.domain.usecase.RefreshSearchUseCase
import iti.gov.trendo.domain.usecase.SearchNewsUseCase
import iti.gov.trendo.domain.usecase.ToggleFavoriteUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { GetNewsUseCase(repository = get()) }
    factory { RefreshNewsUseCase(repository = get()) }

    factory { SearchNewsUseCase(repository = get()) }
    factory { RefreshSearchUseCase(repository = get()) }

    factory { GetNewsByIdUseCase(repository = get()) }

    factory { GetFavoriteNewsUseCase(repository = get()) }
    factory { ToggleFavoriteUseCase(repository = get()) }

    factory { GetCategoriesUseCase(repository = get()) }
    factory { RefreshCategoriesUseCase(repository = get()) }
    factory { GetRegionsUseCase(repository = get()) }
    factory { RefreshRegionsUseCase(repository = get()) }

    factory { ClearExpiredCacheUseCase(repository = get()) }
    factory { ClearCacheUseCase(repository = get()) }
}
