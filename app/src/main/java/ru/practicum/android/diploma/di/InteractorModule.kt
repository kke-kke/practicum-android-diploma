package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.SharedFiltersInteractor
import ru.practicum.android.diploma.domain.SharedFiltersInteractorImpl
import ru.practicum.android.diploma.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.domain.impl.SearchVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.domain.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.interactor.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.interactor.VacancyDetailsInteractor

val interactorModule = module {
    factory<SearchVacanciesInteractor> {
        SearchVacanciesInteractorImpl(
            searchVacanciesRepository = get()
        )
    }

    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }

    factory<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    factory<SharedFiltersInteractor> {
        SharedFiltersInteractorImpl(
            filtersRepository = get()
        )
    }
}
