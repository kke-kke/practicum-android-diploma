package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.impl.SearchVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository

val repositoryModule = module {
    single<SearchVacanciesRepository> {
        SearchVacanciesRepositoryImpl(
            apiService = get()
        )
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get(), get())
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }
}
