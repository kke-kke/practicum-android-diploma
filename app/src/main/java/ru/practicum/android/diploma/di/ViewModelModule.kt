package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favourites.FavouritesViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel

val viewModelModule = module {
    viewModel {
        SearchViewModel(
            searchVacanciesInteractor = get(),
            context = androidApplication()
        )
    }

    viewModel {
        VacancyDetailsViewModel(
            vacancyDetailsInteractor = get(),
            favoritesInteractor = get()
        )
    }

    viewModel {
        FavouritesViewModel(
            favoritesInteractor = get()
        )
    }
}
