package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow

interface SearchVacanciesInteractor {
    fun searchVacancies(
        text: String,
        page: Int,
        perPage: Int
    ): Flow<SearchVacanciesResult>?
}
