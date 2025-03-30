package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.impl.SearchVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.impl.SharedFiltersRepositoryImpl
import ru.practicum.android.diploma.data.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.storage.SharedFiltersRepository

// NEW
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.data.impl.FilterRepositoryImpl
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.data.impl.IndustryRepositoryImpl
import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.data.impl.AreasRepositoryImpl

val repositoryModule = module {
    single<SearchVacanciesRepository> {
        SearchVacanciesRepositoryImpl(
            apiService = get(),
            filterRepository = get()
        )
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get())
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }

    single<SharedFiltersRepository> {
        SharedFiltersRepositoryImpl(
            filtersStorage = get()
        )
    }

    single<FilterRepository> {
        FilterRepositoryImpl(get())
    }

    single<IndustryRepository> {
        IndustryRepositoryImpl(get())
    }

    single<AreasRepository> {
        AreasRepositoryImpl(get())
    }
}
