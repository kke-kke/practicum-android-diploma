package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.favourites.FavouritesViewModel
import ru.practicum.android.diploma.presentation.filters.CountryViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.filters.FilterViewModel
import ru.practicum.android.diploma.presentation.filters.IndustryViewModel
import ru.practicum.android.diploma.presentation.filters.RegionViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel(
            searchVacanciesInteractor = get(),
            context = androidApplication(),
            filtersInteractor = get(),
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

    viewModel {
        FilterViewModel(
            filtersInteractor = get()
        )
    }

    viewModel { (industryName: Industry) ->
        IndustryViewModel(
            industryName = industryName,
            industryInteractor = get()
        )
    }

    viewModel {
        CountryViewModel(
            areasInteractor = get()
        )
    }

    viewModel {
        RegionViewModel(
            areasInteractor = get()
        )
    }
}
