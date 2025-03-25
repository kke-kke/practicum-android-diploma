package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.impl.SearchVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacancyDetailsInteractorImpl
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
}
