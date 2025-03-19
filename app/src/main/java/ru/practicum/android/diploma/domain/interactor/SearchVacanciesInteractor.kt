package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow

interface SearchVacanciesInteractor {
    fun searchVacancies(queryMap: Map<String, String>): Flow<SearchVacanciesResult>?
}
