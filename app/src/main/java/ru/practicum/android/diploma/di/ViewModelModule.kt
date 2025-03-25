package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favourites.FavouritesViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel

val viewModelModule = module {
    viewModel {
        SearchViewModel(get(), androidApplication())
    }

    viewModel {
        VacancyDetailsViewModel(get(), get())
    }

    viewModel {
        FavouritesViewModel(get())
    }
}
