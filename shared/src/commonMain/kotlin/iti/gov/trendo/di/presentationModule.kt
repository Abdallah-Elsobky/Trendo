package iti.gov.trendo.di

import iti.gov.trendo.presentation.ui.details.DetailsViewModel
import iti.gov.trendo.presentation.ui.favorite.FavoriteViewModel
import iti.gov.trendo.presentation.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        HomeViewModel(
            getNewsUseCase = get(),
            refreshNewsUseCase = get(),
            searchNewsUseCase = get(),
            refreshSearchUseCase = get(),
            getCategoriesUseCase = get(),
            refreshCategoriesUseCase = get(),
            getRegionsUseCase = get(),
            refreshRegionsUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
    viewModel {
        DetailsViewModel(
            getNewsByIdUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
    viewModel {
        FavoriteViewModel(
            getFavoriteNewsUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
}
